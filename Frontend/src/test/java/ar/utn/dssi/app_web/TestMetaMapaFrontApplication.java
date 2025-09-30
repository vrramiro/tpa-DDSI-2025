package ar.utn.dssi.app_web;

import org.springframework.boot.SpringApplication;

public class TestMetaMapaFrontApplication {

	public static void main(String[] args) {
		SpringApplication.from(MetaMapaFrontApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
