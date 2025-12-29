package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.EstadoHecho;
import ar.utn.dssi.app_web.dto.input.HechoPageResponseDTO;
import ar.utn.dssi.app_web.dto.input.HechoRequest;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.input.ProvinciaInputDTO;
import ar.utn.dssi.app_web.dto.output.EstadoHechoOutputDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.dto.output.UbicacionOutputDTO;
import ar.utn.dssi.app_web.error.NotFoundException;
import ar.utn.dssi.app_web.error.ServicioNormalizadorException;
import ar.utn.dssi.app_web.services.internal.WebApiCallerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class GestionHechosApiService {

  private static final Logger log = LoggerFactory.getLogger(GestionHechosApiService.class);

  private final WebApiCallerService webApiCallerService;
  private final String normalizadorServiceUrl;
  private final String fuenteDinamicaServiceUrl;
  private final String agregadorServiceUrl;
  private final String fuenteEstaticaServiceUrl;


  public GestionHechosApiService(
          WebApiCallerService webApiCallerService,
          @Value("${normalizador.service.url}") String normalizadorServiceUrl,
          @Value("${fuenteDinamica.service.url}") String fuenteDinamicaServiceUrl,
          @Value("${agregador.service.url}") String agregadorServiceUrl,
          @Value("${fuenteEstatica.service.url}") String fuenteEstaticaServiceUrl
  ) {
    this.webApiCallerService = webApiCallerService;
    this.normalizadorServiceUrl = normalizadorServiceUrl;
    this.fuenteDinamicaServiceUrl = fuenteDinamicaServiceUrl;
    this.agregadorServiceUrl = agregadorServiceUrl;
    this.fuenteEstaticaServiceUrl = fuenteEstaticaServiceUrl;
  }

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

  public List<ProvinciaInputDTO> obtenerProvincias() {
    String url = UriComponentsBuilder
            .fromUriString(normalizadorServiceUrl)
            .path("/ubicacion/provincias")
            .toUriString();
    try {
      return webApiCallerService.getListPublic(url, ProvinciaInputDTO.class);
    } catch (WebClientException e) {
      throw new ServicioNormalizadorException("Error de conexión con servicio normalizador", e);
    }
  }

  public Boolean crearHecho(HechoRequest hechoRequest) {
    String url = UriComponentsBuilder
            .fromUriString(fuenteDinamicaServiceUrl)
            .path("/hechos")
            .toUriString();

    try {
      HechoRequest dtoSinArchivos = new HechoRequest();
      dtoSinArchivos.setTitulo(hechoRequest.getTitulo());
      dtoSinArchivos.setDescripcion(hechoRequest.getDescripcion());
      dtoSinArchivos.setIdCategoria(hechoRequest.getIdCategoria());
      dtoSinArchivos.setLatitud(hechoRequest.getLatitud());
      dtoSinArchivos.setLongitud(hechoRequest.getLongitud());
      dtoSinArchivos.setFechaAcontecimiento(hechoRequest.getFechaAcontecimiento());

      webApiCallerService.post(url, dtoSinArchivos, Void.class);
      return true;
    } catch (Exception e) {
      log.error("Error al crear hecho: {}", e.getMessage(), e);
      return false;
    }
  }

  public Boolean crearHechoEstatica(MultipartFile archivo) {
    System.out.println("entre a controlador importar gestion hechos api service");

    String url = UriComponentsBuilder
            .fromUriString(fuenteEstaticaServiceUrl)
            .path("/hechos/importar")
            .toUriString();

    try {
      webApiCallerService.postMultipart(url, archivo, "archivo", Void.class);
      return true;
    } catch (Exception e) {
      log.error("Error al crear hecho: {}", e.getMessage(), e);
      return false;
    }
  }

  public List<HechoOutputDTO> obtenerHechos(
          LocalDate fechaReporteDesde,
          LocalDate fechaReporteHasta,
          LocalDate fechaAcontecimientoDesde,
          LocalDate fechaAcontecimientoHasta,
          Long idCategoria,
          String provincia) {

    UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString(agregadorServiceUrl)
            .path("/hechos");

    if (fechaReporteDesde != null) {
      builder.queryParam("fechaReporteDesde", fechaReporteDesde);
    }
    if (fechaReporteHasta != null) {
      builder.queryParam("fechaReporteHasta", fechaReporteHasta);
    }
    if (fechaAcontecimientoDesde != null) {
      builder.queryParam("fechaAcontecimientoDesde", fechaAcontecimientoDesde);
    }
    if (fechaAcontecimientoHasta != null) {
      builder.queryParam("fechaAcontecimientoHasta", fechaAcontecimientoHasta);
    }
    if (provincia != null && !provincia.isEmpty()) {
      builder.queryParam("provincia", provincia);
    }
    if (idCategoria != null) {
        builder.queryParam("id_categoria", idCategoria);
    }

    String url = builder.build().toUriString();

    try {
      return webApiCallerService.getListPublic(url, HechoOutputDTO.class);
    } catch (Exception e) {
      log.error("Error al obtener hechos desde el agregador", e);
      return Collections.emptyList();
    }
  }

  public HechoOutputDTO obtenerHechoPorId(Long id) {
    String url = UriComponentsBuilder
            .fromUriString(agregadorServiceUrl)
            .path("/hechos/{id}")
            .buildAndExpand(id)
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

  public Boolean editarHecho(Long id, HechoRequest hechoRequest) {
    String url = UriComponentsBuilder
            .fromUriString(fuenteDinamicaServiceUrl)
            .path("/hechos/{id}")
            .buildAndExpand(id)
            .toUriString();

    try {
      HechoRequest dtoSinArchivos = new HechoRequest();
      dtoSinArchivos.setTitulo(hechoRequest.getTitulo());
      dtoSinArchivos.setDescripcion(hechoRequest.getDescripcion());
      dtoSinArchivos.setIdCategoria(hechoRequest.getIdCategoria());
      dtoSinArchivos.setLatitud(hechoRequest.getLatitud());
      dtoSinArchivos.setLongitud(hechoRequest.getLongitud());
      dtoSinArchivos.setFechaAcontecimiento(hechoRequest.getFechaAcontecimiento());

      webApiCallerService.put(url, dtoSinArchivos, Void.class);
      return true;
    } catch (Exception e) {
      log.error("Error al editar hecho {}: {}", id, e.getMessage());
      return false;
    }
  }

  //TODO VER SI HACE FALTA CUANDO ESTE LISTO EL BACK
  public PageResponseDTO<HechoOutputDTO> buscarProximosHechosAPaginar(Integer page) {
    String url = UriComponentsBuilder
            .fromUriString(agregadorServiceUrl)
            .path("/hechos")
            .queryParam("page", page)
            .toUriString();

    return (PageResponseDTO<HechoOutputDTO>)
            webApiCallerService.get(url, PageResponseDTO.class);
  }

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

  public List<HechoOutputDTO> obtenerMisHechos() {
    String url = UriComponentsBuilder
            .fromUriString(agregadorServiceUrl)
            .path("/hechos/misHechos")
            .toUriString();

    try {
      return webApiCallerService.getList(url, HechoOutputDTO.class);
    } catch (Exception e) {
      log.error("Error al obtener mis hechos: {}", e.getMessage());
      return Collections.emptyList();
    }
  }

  public PageResponseDTO<HechoOutputDTO> listarHechosDeColeccion(String handle, Integer page) {
    String url = UriComponentsBuilder
            .fromUriString(agregadorServiceUrl)
            .path("/public/colecciones/{handle}/hechos")
            .queryParam("page", page)
            .buildAndExpand(handle)
            .toUriString();

    try {
      return webApiCallerService.getPublic(url, HechoPageResponseDTO.class);

    } catch (Exception e) {
      log.error("Error al listar hechos de la colección {}: {}", handle, e.getMessage());
      return new PageResponseDTO<>();
    }
  }

}
