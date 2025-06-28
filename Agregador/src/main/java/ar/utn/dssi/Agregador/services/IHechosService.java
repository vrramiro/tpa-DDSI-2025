package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IHechosService {
    //CRUD
    Hecho crearHecho(HechoInputDTO hechoInputDTO, Long IDFuente);
    List<HechoOutputDTO> obtenerHechos();
    HechoOutputDTO obtenerHechoPorId(Long idHecho);
    Mono<Void> actualizarHechos();
    void eliminarHecho(Long IDHecho);

    //AUX
    void guardarHecho(Hecho hecho);
    HechoOutputDTO hechoOutputDTO(Hecho hecho);
}
