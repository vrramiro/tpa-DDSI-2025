package ar.utn.dssi.app_web.services.Interfaces;

import ar.utn.dssi.app_web.dto.EstadoHecho;
import ar.utn.dssi.app_web.dto.input.HechoInputDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;

import java.util.Optional;

public interface IHechoService {
    PageResponseDTO<HechoOutputDTO> listarHechosDeColeccion(long coleccionId, int page, int size, String filtro, String sort);
    Boolean crearHecho(HechoInputDTO hecho);
    Optional<HechoOutputDTO> obtenerHechoPorId(Long id);
    void cambiarEstadoHecho(Long id, EstadoHecho nuevoEstado);
    Boolean editarHecho(Long id, HechoInputDTO hechoInputDTO);
    public PageResponseDTO<HechoOutputDTO> listarHechos(int page, int size, String filtro, String sort);
}
