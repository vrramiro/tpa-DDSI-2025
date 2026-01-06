package ar.utn.dssi.FuenteDinamica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FuenteDinamicaApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FuenteDinamicaApplication.class);
        app.setWebApplicationType(WebApplicationType.SERVLET); // Esto arregla el conflicto
        app.run(args);
    }
}
