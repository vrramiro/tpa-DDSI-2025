package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.EstadoHecho;
import ar.utn.dssi.app_web.dto.input.HechoInputDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.EstadoHechoOutputDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.dto.output.UbicacionOutputDTO;
import ar.utn.dssi.app_web.error.NotFoundException;
import ar.utn.dssi.app_web.error.ServicioNormalizadorException;
import ar.utn.dssi.app_web.services.internal.WebApiCallerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class GestionHechosApiService {

  private static final Logger log = LoggerFactory.getLogger(GestionHechosApiService.class);
  private final WebClient webClient; // se usa para usuarios nada mas
  private final WebApiCallerService webApiCallerService;
  private final String normalizadorSeriviceUrl;
  private final String fuenteDinamicaSeriviceUrl;
  private final String agregadorSeriviceUrl;

  @Autowired
  public GestionHechosApiService(
          WebApiCallerService webApiCallerService,
          @Value("${normalizador.service.url}")String normalizadorSeriviceUrl,
          @Value("${fuenteDinamica.service.url}")String fuenteDinamicaSeriviceUrl,
          @Value("${agregador.service.url}")String agregadorSeriviceUrl) {
    this.webClient = WebClient.builder().build();
    this.webApiCallerService = webApiCallerService;
    this.normalizadorSeriviceUrl = normalizadorSeriviceUrl;
    this.fuenteDinamicaSeriviceUrl = fuenteDinamicaSeriviceUrl;
    this.agregadorSeriviceUrl = agregadorSeriviceUrl;
  }

  public UbicacionOutputDTO obtenerUbicacion (Double latitud, Double longitud) {
    String url = UriComponentsBuilder
            .fromUriString(normalizadorSeriviceUrl)
            .path("/ubicacion/normalizar")
            .queryParam("latitud", latitud)
            .queryParam("longitud", longitud)
            .toUriString();

    try {
      UbicacionOutputDTO response = webClient
              .get()
              .uri(url)
              .retrieve()
              .bodyToMono(UbicacionOutputDTO.class)
              .block();

      validarUbicacion(response);
      return response;
    } catch (WebClientException e) {
      throw new ServicioNormalizadorException("Error de conexión con servicio normalizador", e);
    } catch (Exception e) {
      log.error("Error inesperado al obtener ubicación", e);
      throw new ServicioNormalizadorException("Error inesperado al normalizar ubicación", e);
    }
  }

  public Boolean crearHecho(HechoInputDTO hechoInputDTO) {
    String url = UriComponentsBuilder
            .fromUriString(fuenteDinamicaSeriviceUrl)
            .path("/hechos")
            .toUriString();

    try {
      HechoInputDTO dtoSinArchivos = new HechoInputDTO();
      dtoSinArchivos.setTitulo(hechoInputDTO.getTitulo());
      dtoSinArchivos.setDescripcion(hechoInputDTO.getDescripcion());
      dtoSinArchivos.setCategoria(hechoInputDTO.getCategoria());
      dtoSinArchivos.setLatitud(hechoInputDTO.getLatitud());
      dtoSinArchivos.setLongitud(hechoInputDTO.getLongitud());
      dtoSinArchivos.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());

      // Log para ver qué se está enviando
      log.info("=== Datos a enviar ===");
      log.info("Titulo: {}", dtoSinArchivos.getTitulo());
      log.info("Descripcion: {}", dtoSinArchivos.getDescripcion());
      log.info("Categoria: {}", dtoSinArchivos.getCategoria());
      log.info("Latitud: {}", dtoSinArchivos.getLatitud());
      log.info("Longitud: {}", dtoSinArchivos.getLongitud());
      log.info("Fecha: {}", dtoSinArchivos.getFechaAcontecimiento());

      webClient
              .post()
              .uri(url)
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(dtoSinArchivos)
              .retrieve()
              .onStatus(
                      status -> status.is4xxClientError(),
                      response -> response.bodyToMono(String.class)
                              .flatMap(errorBody -> {
                                log.error("Error 4xx del backend: {}", errorBody);
                                return Mono.error(new RuntimeException("Error del backend: " + errorBody));
                              })
              )
              .bodyToMono(Void.class)
              .block();
      return true;
    } catch (Exception e) {
      log.error("Error completo al crear hecho: ", e);
      return false;
    }
  }

  public HechoOutputDTO obtenerHechoPorId(Long id) {
    String url = UriComponentsBuilder
            .fromUriString(agregadorSeriviceUrl)
            .path("/hecho")
            .queryParam("id", id)
            .toUriString();

    try {
      HechoOutputDTO response = webClient
              .get()
              .uri(url)
              .retrieve()
              .bodyToMono(HechoOutputDTO.class)
              .block();

      if (response == null) {
        throw new NotFoundException("Hecho", id.toString());
      }
      return response;
    } catch (Exception e) {
      log.error("Error al obtener hecho con id {}: {}", id, e.getMessage());
      throw new NotFoundException("Hecho", id.toString());
    }
  }

  public void cambiarEstadoHecho(Long id, EstadoHecho nuevoEstado) {
    String url = UriComponentsBuilder
            .fromUriString(agregadorSeriviceUrl)
            .path("/hecho/{id}/estado")
            .buildAndExpand(id)
            .toUriString();

    try {
      EstadoHechoOutputDTO estado = new EstadoHechoOutputDTO();
      estado.setEstado(nuevoEstado);

      webClient
              .put()
              .uri(url)
              .bodyValue(estado)
              .retrieve()
              .bodyToMono(Void.class)
              .block();
    } catch (Exception e) {
      throw new RuntimeException("Error al cambiar el estado del hecho", e);
    }
  }

  public Boolean editarHecho(Long id, HechoInputDTO hechoInputDTO) {
    String url = UriComponentsBuilder
            .fromUriString(fuenteDinamicaSeriviceUrl)
            .path("/hechos/{id}")
            .buildAndExpand(id)
            .toUriString();

    try {
      // Crear un dto sin los archivos multimedia
      HechoInputDTO dtoSinArchivos = new HechoInputDTO();
      dtoSinArchivos.setTitulo(hechoInputDTO.getTitulo());
      dtoSinArchivos.setDescripcion(hechoInputDTO.getDescripcion());
      dtoSinArchivos.setCategoria(hechoInputDTO.getCategoria());
      dtoSinArchivos.setLatitud(hechoInputDTO.getLatitud());
      dtoSinArchivos.setLongitud(hechoInputDTO.getLongitud());
      dtoSinArchivos.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());
      // NO seteamos contenidoMultimedia

      webClient
              .put()
              .uri(url)
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(dtoSinArchivos)
              .retrieve()
              .bodyToMono(Void.class)
              .block();
      return true;
    } catch (Exception e) {
      log.error("Error al editar hecho {}: {}", id, e.getMessage());
      return false;
    }
  }

  public PageResponseDTO<HechoOutputDTO> buscarProximosHechosAPaginar(int page, int size, String filtro, String sort) {
    String url = UriComponentsBuilder
            .fromUriString(agregadorSeriviceUrl)
            .path("/hechos")
            .queryParam("page", page)
            .queryParam("size", size)
            .queryParamIfPresent("filtro", Optional.ofNullable(filtro).filter(s -> !s.isBlank()))
            .queryParamIfPresent("sort", Optional.ofNullable(sort))
            .toUriString();

    PageResponseDTO<HechoOutputDTO> response = webClient
            .get()
            .uri(url)
            .retrieve()
            .bodyToMono(PageResponseDTO.class)
            .block();

    return response;
  }

/*
    public Boolean crearHecho(HechoInputDTO hechoInputDTO) {
        String url = UriComponentsBuilder
                .fromUriString(fuenteDinamicaSeriviceUrl)
                .path("/hecho")
                .toUriString();

        try {
            webApiCallerService.post(url, hechoInputDTO, Void.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UbicacionOutputDTO obtenerUbicacion (Double latitud, Double longitud) {
        String url = UriComponentsBuilder
                .fromUriString(normalizadorSeriviceUrl)
                .path("/ubicacion/normalizar")
                .queryParam("latitud", latitud)
                .queryParam("longitud", longitud)
                .toUriString();

        try {
            UbicacionOutputDTO response = webApiCallerService.get(url, UbicacionOutputDTO.class);
            validarUbicacion(response);
            return response;
        } catch (WebClientException e) {  // Más específico
            throw new ServicioNormalizadorException("Error de conexión con servicio normalizador", e);
        } catch (Exception e) {
            log.error("Error inesperado al obtener ubicación", e);
            throw new ServicioNormalizadorException("Error inesperado al normalizar ubicación", e);
        }
    }

    public HechoOutputDTO obtenerHechoPorId(Long id) {
        String url = UriComponentsBuilder
                .fromUriString(agregadorSeriviceUrl)
                .path("/hecho")
                .queryParam("id", id)
                .toUriString();

        try {
            HechoOutputDTO response = webApiCallerService.get(url, HechoOutputDTO.class);
            if (response == null) {
                throw new NotFoundException("Hecho", id.toString());
            }
            return response;

        } catch (Exception e) {
            log.error("Error al obtener hecho con id {}: {}", id, e.getMessage());
            throw new NotFoundException("Hecho", id.toString());
        }
    }

    public void cambiarEstadoHecho(Long id, EstadoHecho nuevoEstado) {
        String url = UriComponentsBuilder
                .fromUriString(agregadorSeriviceUrl)
                .path("/hecho/{id}/estado")
                .buildAndExpand(id)
                .toUriString();

        try {
            EstadoHechoOutputDTO estado = new EstadoHechoOutputDTO();
            estado.setEstado(nuevoEstado);
            webApiCallerService.put(url, estado, Void.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al cambiar el estado del hecho", e);
        }
    }

    public Boolean editarHecho(Long id, HechoInputDTO hechoInputDTO) {
        String url = UriComponentsBuilder
                .fromUriString(fuenteDinamicaSeriviceUrl)
                .path("/hechos/{id}")
                .buildAndExpand(id)
                .toUriString();

        try {
            webApiCallerService.put(url, hechoInputDTO, Void.class);
            return true;
        } catch (Exception e) {
            log.error("Error al editar hecho {}: {}", id, e.getMessage());
            return false;
        }
    }

    public PageResponseDTO<HechoOutputDTO> buscarProximosHechosAPaginar(int page, int size, String filtro, String sort) {

        String url = UriComponentsBuilder
                .fromUriString(agregadorSeriviceUrl)
                .path("/hechos")
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParamIfPresent("filtro", Optional.ofNullable(filtro).filter(s -> !s.isBlank()))
                .queryParamIfPresent("sort", Optional.ofNullable(sort))
                .toUriString();

        @SuppressWarnings("unchecked") //SOLO PARA QUE NO JODA EL COMPILADOR
        PageResponseDTO<HechoOutputDTO> response =
                (PageResponseDTO<HechoOutputDTO>) webApiCallerService.get(url, PageResponseDTO.class);

        return response;
    }

/**********************************************************************************************************************/
/*************************************************LO DE ABAJO ES EXTRA*************************************************/
  /**********************************************************************************************************************/

  private void validarUbicacion(UbicacionOutputDTO ubicacion) {
    if (esVacioONulo(ubicacion.getPais())) {
      throw new NotFoundException("País no encontrado");
    }
    if (esVacioONulo(ubicacion.getProvincia())) {
      throw new NotFoundException("Provincia no encontrada");
    }
    if (esVacioONulo(ubicacion.getCiudad())) {
      throw new NotFoundException("Ciudad no encontrada");
    }
  }

  private boolean esVacioONulo(String valor) {
    return valor == null || valor.trim().isEmpty();
  }

  public Boolean ubicacionValida(Double latitud, Double longitud) {
    try {
      obtenerUbicacion(latitud, longitud);
      return true;
    } catch (NotFoundException e) {
      return false;
    } catch (Exception e) {
      throw new RuntimeException("Error al verificar la ubicación: " + e.getMessage(), e);
    }
  }
}