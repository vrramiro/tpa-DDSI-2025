package ar.utn.dssi.Agregador.models.entities.fuente;

import lombok.Getter;
import org.springframework.web.reactive.function.client.WebClient;


@Getter
public class Fuente {
  private Long id;
  private String nombre;
  private ITipoFuente tipoFuente;
  private String baseUrl;
}