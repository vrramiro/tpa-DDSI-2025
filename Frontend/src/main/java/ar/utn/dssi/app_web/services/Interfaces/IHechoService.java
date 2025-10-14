package ar.utn.dssi.app_web.services.Interfaces;

import ar.utn.dssi.app_web.dto.EstadoHecho;
import ar.utn.dssi.app_web.dto.input.HechoRequest;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.input.ProvinciaInputDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IHechoService {
    PageResponseDTO<HechoOutputDTO> listarHechosDeColeccion(String handle);
    Boolean crearHecho(HechoRequest hecho);
    Optional<HechoOutputDTO> obtenerHechoPorId(Long id);
    void cambiarEstadoHecho(Long id, EstadoHecho nuevoEstado);
    Boolean editarHecho(Long idHecho, HechoRequest hechoRequest);
    PageResponseDTO<HechoOutputDTO> listarHechos(Integer page);
    Boolean crearHechoEstatico(MultipartFile archivo);
    List<HechoOutputDTO> obtenerHechos(LocalDate fechaReporteDesde, LocalDate fechaReporteHasta, Long idCategoria, String provincia);
    List<ProvinciaInputDTO> obtenerProvincias();
}
