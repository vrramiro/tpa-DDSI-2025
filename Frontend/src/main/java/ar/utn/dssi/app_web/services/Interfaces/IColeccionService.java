package ar.utn.dssi.app_web.services.Interfaces;

import ar.utn.dssi.app_web.dto.input.ColeccionResponseDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.ColeccionRequestDTO;

public interface IColeccionService {
    PageResponseDTO<ColeccionResponseDTO> listarColecciones(int page, int size, String filtro, String sort);
    ColeccionResponseDTO obtenerColeccion(long id);
    ColeccionResponseDTO crearColeccion(ColeccionRequestDTO coleccion);
    void eliminarColeccion(long id);
    void actualizarColeccion(long id);
}
