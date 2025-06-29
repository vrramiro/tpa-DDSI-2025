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
    //CRUD
    ColeccionOutputDTO crearColeccion(ColeccionInputDTO coleccionInputDTO);
    List<HechoOutputDTO> leerColeccion (String handle);
    ColeccionOutputDTO actualizarColeccion (String handle, ColeccionInputDTO coleccionInputDTO);
    void eliminarColeccion(String handle);

    List<ColeccionOutputDTO> obtenerColecciones();
    List<HechoOutputDTO> obtenerHechosDeColeccion(String handle);

    void agregarFuente(Long idFuente,String handle);
    void eliminarFuente(Long idFuente, String handle);

    Mono<Void> refrescarColecciones(Hecho hecho);

    void agregarCriterioDePertenencia(ICriterioDePertenencia nuevoCriterio, String handle);
    void eliminarCriterioDePertenencia(ICriterioDePertenencia nuevoCriterio, String handle);

}
