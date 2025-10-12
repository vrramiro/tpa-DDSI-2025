package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FiltroInputDTO;
import ar.utn.dssi.Agregador.models.entities.Filtro;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;
import org.apache.logging.log4j.simple.internal.SimpleProvider;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IHechosService {
    //CRUD
    List<HechoOutputDTO> obtenerHechos();
    HechoOutputDTO obtenerHechoPorId(Long idHecho);
    void eliminarHecho(Long IDHecho);

    //AUX
     void importarNuevosHechos();
}
