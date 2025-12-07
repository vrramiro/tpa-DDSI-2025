package ar.utn.dssi.app_web.services.impl;

import ar.utn.dssi.app_web.dto.input.ColeccionResponseDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.ColeccionRequestDTO;
import ar.utn.dssi.app_web.error.NotFoundException;
import ar.utn.dssi.app_web.services.GestionColeccionApiService;
import ar.utn.dssi.app_web.services.Interfaces.IColeccionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ColeccionService implements IColeccionService {

  private final GestionColeccionApiService gestionColeccionApiService;

  public ColeccionService(GestionColeccionApiService gestionColeccionApiService) {
    this.gestionColeccionApiService = gestionColeccionApiService;
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
  public void eliminarColeccion(Long id) {
    String handle = "";
    gestionColeccionApiService.obtenerColeccion(handle);
    gestionColeccionApiService.eliminarColeccion(id);
  }

  @Override
  public ColeccionResponseDTO actualizarColeccion(String handle, ColeccionRequestDTO coleccion) {
    return gestionColeccionApiService.actualizarColeccion(handle, coleccion);
  }
}
