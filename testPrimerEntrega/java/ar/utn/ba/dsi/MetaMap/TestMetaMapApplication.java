package ar.utn.ba.dsi.MetaMap;

import org.springframework.boot.SpringApplication;

public class TestMetaMapApplication {

  public static void main(String[] args) {
    SpringApplication.from(MetaMapApplication::main).with(TestcontainersConfiguration.class).run(args);
  }

}
