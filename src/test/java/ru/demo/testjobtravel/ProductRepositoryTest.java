package ru.demo.testjobtravel;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;
import ru.demo.testjobtravel.model.Product;
import ru.demo.testjobtravel.repository.ProductRepository;

@SpringBootTest
public class ProductRepositoryTest {

  @Autowired
  private ProductRepository repo;

  @Test
  void shouldSaveAndRetrieveProduct() {
    Product product = new Product(null, "Test Product", "Description", BigDecimal.valueOf(29.99));

    // Test save
    StepVerifier.create(repo.save(product))
        .expectNextMatches(p -> p.id() != null)
        .verifyComplete();

    // Test findById
    StepVerifier.create(repo.findAll().collectList())
        .expectNextMatches(products -> {
          return products.size() == 1 && products.get(0).name().equals("Test Product");
        })
        .verifyComplete();
  }

}
