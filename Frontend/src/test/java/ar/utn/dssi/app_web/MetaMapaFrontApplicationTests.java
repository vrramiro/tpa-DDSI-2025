package ar.utn.dssi.app_web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class MetaMapaFrontApplicationTests {

  @Test
  void contextLoads() {
  }

}
