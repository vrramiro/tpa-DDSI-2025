package ar.utb.ba.dsi.Normalizador;

import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;
import ar.utb.ba.dsi.Normalizador.models.repository.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class NormalizadorApplication {
    public static void main(String[] args) {SpringApplication.run(NormalizadorApplication.class, args);
    }

}

