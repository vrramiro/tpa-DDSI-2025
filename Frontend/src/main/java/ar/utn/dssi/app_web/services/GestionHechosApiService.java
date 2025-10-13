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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class GestionHechosApiService {

  private static final Logger log = LoggerFactory.getLogger(GestionHechosApiService.class);

  private final WebApiCallerService webApiCallerService;
  private final String normalizadorServiceUrl;
  private final String fuenteDinamicaServiceUrl;
  private final String agregadorServiceUrl;

  public GestionHechosApiService(
          WebApiCallerService webApiCallerService,
          @Value("${normalizador.service.url}") String normalizadorServiceUrl,
          @Value("${fuenteDinamica.service.url}") String fuenteDinamicaServiceUrl,
          @Value("${agregador.service.url}") String agregadorServiceUrl
  ) {
    this.webApiCallerService = webApiCallerService;
    this.normalizadorServiceUrl = normalizadorServiceUrl;
    this.fuenteDinamicaServiceUrl = fuenteDinamicaServiceUrl;
    this.agregadorServiceUrl = agregadorServiceUrl;
  }

  /** =========================================
   *  UBICACIÓN
   *  ========================================= */
  public UbicacionOutputDTO obtenerUbicacion(Double latitud, Double longitud) {
    String url = UriComponentsBuilder
            .fromUriString(normalizadorServiceUrl)
            .path("/ubicacion/normalizar")
            .queryParam("latitud", latitud)
            .queryParam("longitud", longitud)
            .toUriString();

    try {
      UbicacionOutputDTO response = webApiCallerService.get(url, UbicacionOutputDTO.class);
      validarUbicacion(response);
      return response;
    } catch (WebClientException e) {
      throw new ServicioNormalizadorException("Error de conexión con servicio normalizador", e);
    } catch (Exception e) {
      log.error("Error inesperado al obtener ubicación", e);
      throw new ServicioNormalizadorException("Error inesperado al normalizar ubicación", e);
    }
  }

  /** =========================================
   *  CREAR HECHO
   *  ========================================= */
  public Boolean crearHecho(HechoInputDTO hechoInputDTO) {
    String url = UriComponentsBuilder
            .fromUriString(fuenteDinamicaServiceUrl)
            .path("/hechos")
            .toUriString();

    try {
      // Clon sin archivos porque me rompen los archivos
      HechoInputDTO dtoSinArchivos = new HechoInputDTO();
      dtoSinArchivos.setTitulo(hechoInputDTO.getTitulo());
      dtoSinArchivos.setDescripcion(hechoInputDTO.getDescripcion());
      dtoSinArchivos.setCategoria(hechoInputDTO.getCategoria());
      dtoSinArchivos.setLatitud(hechoInputDTO.getLatitud());
      dtoSinArchivos.setLongitud(hechoInputDTO.getLongitud());
      dtoSinArchivos.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());

      webApiCallerService.post(url, dtoSinArchivos, Void.class);
      return true;
    } catch (Exception e) {
      log.error("Error al crear hecho: {}", e.getMessage(), e);
      return false;
    }
  }

  /** =========================================
   *  OBTENER HECHO POR ID
   *  ========================================= */
  public HechoOutputDTO obtenerHechoPorId(Long id) {
    String url = UriComponentsBuilder
            .fromUriString(agregadorServiceUrl)
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

  /** =========================================
   *  CAMBIAR ESTADO HECHO
   *  ========================================= */
  public void cambiarEstadoHecho(Long id, EstadoHecho nuevoEstado) {
    String url = UriComponentsBuilder
            .fromUriString(agregadorServiceUrl)
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

  /** =========================================
   *  EDITAR HECHO
   *  ========================================= */
  public Boolean editarHecho(Long id, HechoInputDTO hechoInputDTO) {
    String url = UriComponentsBuilder
            .fromUriString(fuenteDinamicaServiceUrl)
            .path("/hechos/{id}")
            .buildAndExpand(id)
            .toUriString();

    try {
      HechoInputDTO dtoSinArchivos = new HechoInputDTO();
      dtoSinArchivos.setTitulo(hechoInputDTO.getTitulo());
      dtoSinArchivos.setDescripcion(hechoInputDTO.getDescripcion());
      dtoSinArchivos.setCategoria(hechoInputDTO.getCategoria());
      dtoSinArchivos.setLatitud(hechoInputDTO.getLatitud());
      dtoSinArchivos.setLongitud(hechoInputDTO.getLongitud());
      dtoSinArchivos.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());

      webApiCallerService.put(url, dtoSinArchivos, Void.class);
      return true;
    } catch (Exception e) {
      log.error("Error al editar hecho {}: {}", id, e.getMessage());
      return false;
    }
  }

  /** =========================================
   *  BUSCAR HECHOS PAGINADOS
   *  ========================================= */
  @SuppressWarnings("unchecked")
  public PageResponseDTO<HechoOutputDTO> buscarProximosHechosAPaginar(int page, int size, String filtro, String sort) {
    String url = UriComponentsBuilder
            .fromUriString(agregadorServiceUrl)
            .path("/hechos")
            .queryParam("page", page)
            .queryParam("size", size)
            .queryParamIfPresent("filtro", Optional.ofNullable(filtro).filter(s -> !s.isBlank()))
            .queryParamIfPresent("sort", Optional.ofNullable(sort))
            .toUriString();

    return (PageResponseDTO<HechoOutputDTO>)
            webApiCallerService.get(url, PageResponseDTO.class);
  }

  /** =========================================
   *  AUXILIARES
   *  ========================================= */
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
