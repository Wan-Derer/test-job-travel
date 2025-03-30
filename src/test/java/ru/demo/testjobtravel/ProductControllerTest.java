package ru.demo.testjobtravel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.demo.testjobtravel.model.Product;

@SpringBootTest
@AutoConfigureWebTestClient
class ProductControllerTest {

  @Autowired
  private WebTestClient client;

  @Test
  void shouldReturnAllProducts() {
    client.get().uri("/api/products")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Product.class);
  }
}
