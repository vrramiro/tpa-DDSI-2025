package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IHechosService {
    Mono<Void> actualizarHechos();
    void eliminarHecho(Long IDHecho);
    HechoOutputDTO obtenerHechoPorId(Long idHecho);
    HechoOutputDTO hechoOutputDTO(Hecho hecho);
    public List<HechoOutputDTO> obtenerHechos();
    public Hecho crearHecho(HechoInputDTO hechoInputDTO, Long IDFuente);

    }
