package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IHechosService {
    void eliminarHecho(Long IDHecho);
    void guardarHecho(Hecho hecho);
    Mono<Void> actualizarHechos();
    HechoOutputDTO obtenerHechoPorId(Long idHecho);
    HechoOutputDTO hechoOutputDTO(Hecho hecho);
    List<HechoOutputDTO> obtenerHechos();
    Hecho crearHecho(HechoInputDTO hechoInputDTO, Long IDFuente);
}
