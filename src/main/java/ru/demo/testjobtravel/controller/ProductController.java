package ru.demo.testjobtravel.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.demo.testjobtravel.model.Product;
import ru.demo.testjobtravel.service.ProductService;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

  private final ProductService service;

  @GetMapping
  public Flux<Product> getAllProducts() {
    log.info("Request to get all products");
    return service.getAllProducts()
        .doOnNext(product -> log.debug("Found product: {}", product))
        .switchIfEmpty(Flux.defer(() -> {
          log.warn("No products found in database");
          return Flux.error(new ResponseStatusException(
              HttpStatus.NOT_FOUND, "No products found"
          ));
        }))
        .doOnError(e -> log.error("Error while fetching products: {}", e.getMessage()));
  }

  @GetMapping("/{id}")
  public Mono<Product> getProductById(@PathVariable Long id) {
    log.info("Request to get product by id: {}", id);
    return service.getProductById(id)
        .doOnSuccess(product -> {
          if (product != null) {
            log.debug("Found product with id {}: {}", id, product);
          }
        })
        .switchIfEmpty(Mono.defer(() -> {
          log.warn("Product with id {} not found", id);
          return Mono.error(new ResponseStatusException(
              HttpStatus.NOT_FOUND, "Product with id " + id + " not found"
          ));
        }))
        .doOnError(e -> log.error("Error while fetching product with id {}: {}", id, e.getMessage()));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Product> createProduct(@RequestBody @Validated Mono<Product> request) {
    return request
        .doOnNext(req -> log.info("Creating new product: {}", req))
        .flatMap(req -> {
          if (req.price().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Invalid price value: {}", req.price());
            return Mono.error(new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Price must be greater than 0"
            ));
          }

          final Product newProduct = new Product(null, req.name(), req.description(), req.price());

          return service.createProduct(newProduct)
              .doOnSuccess(p -> log.info("Created product with id: {}", p.id()));
        })
        .onErrorResume(e -> {
          log.error("Failed to create product: {}", e.getMessage());
          return Mono.error(e);
        });
  }


  @ExceptionHandler(ResponseStatusException.class)
  public Mono<Map<String, Object>> handleException(ResponseStatusException e) {
    log.error("Handling exception: {}", e.getMessage());
    return Mono.just(Map.of(
        "timestamp", Instant.now(),
        "status", e.getStatusCode().value(),
        "error", Objects.requireNonNull(e.getReason()),
        "message", e.getMessage()
    ));
  }

}