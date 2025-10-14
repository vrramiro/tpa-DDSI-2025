package ar.utn.dssi.app_web.services.impl;

import ar.utn.dssi.app_web.dto.input.ColeccionResponseDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.ColeccionRequestDTO;
import ar.utn.dssi.app_web.error.NotFoundException;
import ar.utn.dssi.app_web.services.GestionColeccionApiService;
import ar.utn.dssi.app_web.services.Interfaces.IColeccionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class ColeccionService implements IColeccionService {

  private final GestionColeccionApiService gestionColeccionApiService;
  private static final int MAX_PAGE_SIZE = 50;

    public ColeccionService(GestionColeccionApiService gestionColeccionApiService) {
        this.gestionColeccionApiService = gestionColeccionApiService;
    }

    @Override
  public PageResponseDTO<ColeccionResponseDTO> listarColecciones(Integer page, Integer size, String filtro, String sort) {

      int safePage = Math.max(0, page);
      int safeSize = size <= 0 ? 12 : Math.min(size, MAX_PAGE_SIZE);

      String safeFiltro = StringUtils.hasText(filtro) ? filtro.trim() : null;
      String safeSort   = StringUtils.hasText(sort)   ? sort.trim()   : null;

      PageResponseDTO<ColeccionResponseDTO> api = gestionColeccionApiService.paginacion(safePage, safeSize, safeFiltro, safeSort);

      return api;
  }

  @Override
  public Optional<ColeccionResponseDTO> obtenerColeccion(Long id) {
      try{
        ColeccionResponseDTO coleccion = gestionColeccionApiService.obtenerColeccion(id);
        return Optional.of(coleccion);
      }
      catch (NotFoundException e) {
        return Optional.empty();
      }
  }

  @Override
  public ColeccionResponseDTO crearColeccion(ColeccionRequestDTO coleccion) {

    return gestionColeccionApiService.crearColeccion(coleccion);
  }

  @Override
  public void eliminarColeccion(Long id) {
    gestionColeccionApiService.obtenerColeccion(id);
    gestionColeccionApiService.eliminarColeccion(id);
  }

  @Override
  public ColeccionResponseDTO actualizarColeccion(Long id) {
      return gestionColeccionApiService.actualizarColeccion(id);
  }

}
