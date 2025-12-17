package ar.utn.dssi.app_web.services.impl;

import ar.utn.dssi.app_web.dto.input.ColeccionResponseDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.ColeccionRequestDTO;
import ar.utn.dssi.app_web.error.NotFoundException;
import ar.utn.dssi.app_web.services.GestionColeccionApiService;
import ar.utn.dssi.app_web.services.Interfaces.IColeccionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ColeccionService implements IColeccionService {

  @Value("${agregador.service.url}")
  private String URL_AGREGADOR;

  private final RestTemplate restTemplate;

  private final GestionColeccionApiService gestionColeccionApiService;


  public ColeccionService(GestionColeccionApiService gestionColeccionApiService, RestTemplate restTemplate) {
    this.gestionColeccionApiService = gestionColeccionApiService;
    this.restTemplate = restTemplate;
  }

  @Override
  public PageResponseDTO<ColeccionResponseDTO> listarColecciones(Integer page, Integer size) {
        return gestionColeccionApiService.obtenerColecciones(page, size);
  }

  @Override
  public Optional<ColeccionResponseDTO> obtenerColeccion(String handle) {
    try {
      ColeccionResponseDTO coleccion = gestionColeccionApiService.obtenerColeccion(handle);
      return Optional.ofNullable(coleccion);
    } catch (NotFoundException e) {
      return Optional.empty();
    }
  }

  @Override
  public ColeccionResponseDTO crearColeccion(ColeccionRequestDTO coleccion) {

    return gestionColeccionApiService.crearColeccion(coleccion);
  }

  @Override
  public void eliminarColeccion(String handle) {
    gestionColeccionApiService.eliminarColeccion(handle);
  }

  @Override
  public ColeccionResponseDTO actualizarColeccion(String handle, ColeccionRequestDTO coleccion) {
    return gestionColeccionApiService.actualizarColeccion(handle, coleccion);
  }

  @Override
  public List<ColeccionResponseDTO> obtenerTodasLasColecciones() {
    return gestionColeccionApiService.obtenerTodasLasColecciones();
  }


  @Override
  public List<ColeccionResponseDTO> obtenerUltimasColecciones(int cantidad){
    String url = String.format(
            "%s/public/colecciones?page=0&size=%d&sort=handle,desc",
            URL_AGREGADOR,
            cantidad
    );

    try {
      ParameterizedTypeReference<PageResponseDTO<ColeccionResponseDTO>> responseType =
              new ParameterizedTypeReference<PageResponseDTO<ColeccionResponseDTO>>() {};

      ResponseEntity<PageResponseDTO<ColeccionResponseDTO>> response =
              restTemplate.exchange(url, HttpMethod.GET, null, responseType);

      if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
        return response.getBody().getContent();
      }

    } catch (Exception e) {
    }

    return Collections.emptyList();
  }
}
