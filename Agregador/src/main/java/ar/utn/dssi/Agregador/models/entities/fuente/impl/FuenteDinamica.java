package ar.utn.dssi.Agregador.models.entities.fuente.impl;

import ar.utn.dssi.Agregador.dto.input.fuentes.HechoFuenteDinamicaInputDTO;
import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.ContenidoMultimedia;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.Ubicacion;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoFuente;
import ar.utn.dssi.Agregador.models.entities.fuente.TipoFuente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FuenteDinamica implements ITipoFuente {

  @Value("${timeout-ms}")
  private Integer timeoutMs = 15000;

  @Override
  public List<Hecho> hechosNuevos(Fuente fuente) {
    LocalDateTime ultimoEnvioFuente = fuente.getUltimaActualizacion();


    return getHechos(fuente.getBaseUrl(), ultimoEnvioFuente)
        .map(hecho -> this.hechoFromInputDTODinamica(hecho, fuente))
        .collectList()
        .block();
  }

  @Override
  public TipoFuente getTipoFuente() {
    return TipoFuente.DINAMICA;
  }

  private Flux<HechoFuenteDinamicaInputDTO> getHechos(String baseUrl, LocalDateTime fechaDesde) {
    WebClient webClient = WebClient.builder()
        .baseUrl(baseUrl + "/hechos")
        .build();

    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .queryParam("fechaDesde", fechaDesde)
            .build())
        .retrieve()
        .bodyToFlux(HechoFuenteDinamicaInputDTO.class)
        .timeout(Duration.ofMillis(timeoutMs))
        .onErrorResume(e -> {
          e.printStackTrace();
          return Flux.empty();
        });
  }


  private Hecho hechoFromInputDTODinamica(HechoFuenteDinamicaInputDTO input, Fuente fuente) {
    Hecho hecho = new Hecho();
    hecho.setIdEnFuente(input.getIdOrigen());
    hecho.setFuente(fuente);
    hecho.setTitulo(input.getTitulo());
    hecho.setDescripcion(input.getDescripcion());
    hecho.setTituloSanitizado(input.getTituloSanitizado());
    hecho.setDescripcionSanitizado(input.getDescripcionSanitizada());

    // Categoria
    Categoria categoria = new Categoria();
    categoria.setId(input.getCategoria().getIdCategoria());
    categoria.setNombre(input.getCategoria().getNombre());
    hecho.setCategoria(categoria);

    // Ubicacion
    Ubicacion ubicacion = new Ubicacion();
    ubicacion.setLatitud(input.getUbicacion().getLatitud());
    ubicacion.setLongitud(input.getUbicacion().getLongitud());
    ubicacion.setPais(input.getUbicacion().getPais());
    ubicacion.setCiudad(input.getUbicacion().getCiudad());
    ubicacion.setProvincia(input.getUbicacion().getProvincia());
    hecho.setUbicacion(ubicacion);

    hecho.setFechaAcontecimiento(input.getFechaAcontecimiento());
    hecho.setFechaCarga(input.getFechaCarga());
    hecho.setVisible(input.getVisible() != null ? input.getVisible() : true);

    // Contenido Multimedia
    if (input.getContenidoMultimedia() != null) {
      hecho.setContenidoMultimedia(
          input.getContenidoMultimedia().stream()
              .map(ContenidoMultimedia::new)
              .collect(Collectors.toList())
      );

      hecho.setAutor(input.getAutor());
    }

    return hecho;
  }
}
