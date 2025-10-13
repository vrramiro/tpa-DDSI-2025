package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.input.ColeccionResponseDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.ColeccionRequestDTO;
import ar.utn.dssi.app_web.services.Interfaces.IColeccionService;
import org.springframework.stereotype.Service;

@Service
public class ColeccionService implements IColeccionService {

  @Override
  public PageResponseDTO<ColeccionResponseDTO> listarColecciones(int page, int size, String filtro, String sort) {
    return null; // TODO casi todo el proceso lo hace GestionColeccionApi
  }

  @Override
  public ColeccionResponseDTO obtenerColeccion(long id) {
    return null;
  }

  @Override
  public ColeccionResponseDTO crearColeccion(ColeccionRequestDTO coleccion) {
    return null;
  }

  @Override
  public void eliminarColeccion(long id) {

  }

  @Override
  public void actualizarColeccion(long id) {

  }
}
