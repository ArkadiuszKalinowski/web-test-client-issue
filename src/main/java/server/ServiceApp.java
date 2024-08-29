package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ServiceApp {
  public static void main(String[] args) {
    SpringApplication.run(ServiceApp.class, args);
  }
}

@RestController
class SampleController {
  @GetMapping("/test")
  Flux<DataBuffer> testEndpoint(@RequestHeader HttpHeaders headers,
                                ServerHttpResponse response) {
    var responseData = "sample123";
    var responseHeaders = response.getHeaders();
    responseHeaders.setContentLength(6);

    if (isRangeRequest(headers)) {
      responseHeaders.set(HttpHeaders.CONTENT_RANGE, "bytes 0-5/9");
      response.setStatusCode(HttpStatus.PARTIAL_CONTENT);
    }

    return Flux.just(new DefaultDataBufferFactory().wrap(responseData.getBytes()));
  }

  private static boolean isRangeRequest(HttpHeaders headers) {
    return headers.containsKey(HttpHeaders.RANGE);
  }
}
