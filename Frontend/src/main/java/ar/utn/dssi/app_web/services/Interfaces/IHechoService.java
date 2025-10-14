package ar.utn.dssi.app_web.services.Interfaces;

import ar.utn.dssi.app_web.dto.EstadoHecho;
import ar.utn.dssi.app_web.dto.input.HechoRequest;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.input.ProvinciaInputDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IHechoService {
    PageResponseDTO<HechoOutputDTO> listarHechosDeColeccion(long coleccionId);
    Boolean crearHecho(HechoInputDTO hecho);

    Optional<HechoOutputDTO> obtenerHechoPorId(Long id);
    void cambiarEstadoHecho(Long id, EstadoHecho nuevoEstado);
    Boolean editarHecho(Long id, HechoRequest hechoRequest);
    public PageResponseDTO<HechoOutputDTO> listarHechos(int page, int size, String filtro, String sort);
    Boolean crearHechoEstatico(MultipartFile archivo);
    void registrarSugerencia(Long id, String sugerencia);
}
