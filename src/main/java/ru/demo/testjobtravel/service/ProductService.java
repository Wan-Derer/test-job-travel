package ru.demo.testjobtravel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.demo.testjobtravel.model.Product;
import ru.demo.testjobtravel.repository.ProductRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository repo;

  public Flux<Product> getAllProducts() {
    return repo.findAll();
  }

  public Mono<Product> getProductById(Long id) {
    return repo.findById(id);
  }

  public Mono<Product> createProduct(Product newProduct) {
    return repo.save(newProduct);
  }

}
