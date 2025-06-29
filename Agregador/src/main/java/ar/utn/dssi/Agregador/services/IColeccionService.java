package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Coleccion;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import ar.utn.dssi.Agregador.models.entities.criterio.ICriterioDePertenencia;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface IColeccionService {
    List<ColeccionOutputDTO> obtenerColecciones();
    void crearColeccion(ColeccionInputDTO coleccionInputDTO);
    List<HechoOutputDTO> obtenerHechosDeColeccion (String handle);
    Mono<Void> refrescarColecciones(Hecho hecho);
    public void agregarCriterioDePertenencia(ICriterioDePertenencia nuevoCriterio, String handle);
}
