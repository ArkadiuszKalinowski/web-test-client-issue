package controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import server.ServiceApp;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = ServiceApp.class)
public class ControllerTest {
  @Autowired
  private WebTestClient webTestClient;

  @Test
  void endpointTest() {
    webTestClient.get()
        .uri("/test")
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentLength(6)
        .expectBody(String.class).isEqualTo("sample");

    webTestClient.get()
        .uri("/test")
        .exchange()
        .expectStatus().isOk();
  }
}
