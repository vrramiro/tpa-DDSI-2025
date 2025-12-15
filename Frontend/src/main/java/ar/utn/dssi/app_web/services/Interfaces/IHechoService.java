package ar.utn.dssi.app_web.services.Interfaces;

import ar.utn.dssi.app_web.dto.EstadoHecho;
import ar.utn.dssi.app_web.dto.input.HechoRequest;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseHechosDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.dto.output.SolicitudEdicionDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IHechoService {
  
    PageResponseDTO<HechoOutputDTO> listarHechosDeColeccion(String handle, Integer page);
    Boolean crearHecho(HechoRequest hechoRequest);
    Boolean crearHechoEstatico(MultipartFile archivo);
    Optional<HechoOutputDTO> obtenerHechoPorId(Long id);
    void cambiarEstadoHecho(Long id, EstadoHecho nuevoEstado);
    Boolean crearSolicitudEdicion(Long idHecho, HechoRequest nuevosDatos);

    PageResponseHechosDTO<HechoOutputDTO> listarHechos(Integer page, Integer size, String estado);

    List<HechoOutputDTO> obtenerHechos(LocalDate desde, LocalDate hasta, Long categoria, String provincia);
    List<String> obtenerProvincias();
    List<HechoOutputDTO> obtenerMisHechos();

    List<SolicitudEdicionDTO> obtenerSolicitudesEdicionPendientes();
    void procesarSolicitudEdicion(Long id, String accion, HechoRequest modificaciones);
    Optional<SolicitudEdicionDTO> obtenerSolicitudEdicionPorId(Long id);
    List<HechoOutputDTO> obtenerHechosRecientes(int limit);
}