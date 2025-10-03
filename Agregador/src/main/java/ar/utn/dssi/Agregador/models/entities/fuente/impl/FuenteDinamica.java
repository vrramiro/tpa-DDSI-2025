package ar.utn.dssi.Agregador.models.entities.fuente.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.fuentes.HechoFuenteDinamicaInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.ContenidoMultimedia;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.Ubicacion;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoFuente;
import lombok.NoArgsConstructor;
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

  @Value("${fuente-dinamica.timeout-ms}")
  private Integer timeoutMs;

  @Override
  public List<Hecho> hechosNuevos(Fuente fuente) {
    LocalDateTime ultimoEnvioFuente = fuente.getUltimaActualizacion();
    LocalDateTime fechaDesde;

    if (ultimoEnvioFuente == null) {
      fechaDesde = LocalDateTime.now();
    } else {
      fechaDesde = ultimoEnvioFuente;
    }

    return getHechos(fuente.getBaseUrl(), fechaDesde)
            .map(this::hechoFromInputDTODinamica)
            .collectList()
            .block();
  }


  private Flux<HechoFuenteDinamicaInputDTO> getHechos(String baseUrl, LocalDateTime fechaDesde) {
    WebClient webClient = WebClient.builder()
            .baseUrl(baseUrl+ "/hechos")
            .build();

    return webClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/nuevos")
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


  private Hecho hechoFromInputDTODinamica(HechoFuenteDinamicaInputDTO input) {
    Hecho hecho = new Hecho();
    hecho.setIdEnFuente(input.getIdExterno());
    hecho.setTitulo(input.getTitulo());
    hecho.setDescripcion(input.getDescripcion());
    hecho.setTituloSanitizado(input.getTituloSanitizado());
    hecho.setDescripcionSanitizado(input.getDescripcionSanitizada());

    // Categoria
      Categoria categoria = new Categoria();
      categoria.setId(input.getCategoria().getId());
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
    hecho.setVisible(true);

    // Contenido Multimedia
    if (input.getContenidoMultimedia() != null) {
      hecho.setContenidoMultimedia(
              input.getContenidoMultimedia().stream()
                      .map(ContenidoMultimedia::new)
                      .collect(Collectors.toList())
      );
    }

    return hecho;
  }
}
