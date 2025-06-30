package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FiltroInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.ICriterioDeFiltrado;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;
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

    public List<HechoOutputDTO> navegacionColeccion(FiltroInputDTO filtroInputDTO, IModoNavegacion modoNavegacion);

    List<ColeccionOutputDTO> obtenerColecciones();
    List<HechoOutputDTO> obtenerHechosDeColeccion(String handle);

    void agregarFuente(Long idFuente,String handle);
    void eliminarFuente(Long idFuente, String handle);

    Mono<Void> refrescarColecciones(Hecho hecho);

    void agregarCriterioDePertenencia(ICriterioDeFiltrado nuevoCriterio, String handle);
    void eliminarCriterioDePertenencia(ICriterioDeFiltrado nuevoCriterio, String handle);

}
