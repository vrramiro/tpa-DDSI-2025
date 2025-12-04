package ar.utn.dssi.app_web.services.Interfaces;

import ar.utn.dssi.app_web.dto.input.ColeccionResponseDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.ColeccionRequestDTO;

import java.util.Optional;

public interface IColeccionService {
    PageResponseDTO<ColeccionResponseDTO> listarColecciones(Integer page, Integer size);
    Optional<ColeccionResponseDTO> obtenerColeccion(String handle);
    ColeccionResponseDTO crearColeccion(ColeccionRequestDTO coleccion);
    void eliminarColeccion(Long id);
    ColeccionResponseDTO actualizarColeccion(Long id);
}
