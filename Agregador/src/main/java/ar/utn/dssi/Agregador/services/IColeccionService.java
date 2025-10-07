package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FiltroInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.ModoNavegacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IColeccionService {
    //CRUD
    ColeccionOutputDTO crearColeccion(ColeccionInputDTO coleccionInputDTO);
    List<ColeccionOutputDTO> obtenerColecciones();
    ColeccionOutputDTO editarColeccion(String handle, ColeccionInputDTO coleccionInputDTO);
    void eliminarColeccion(String handle);
    List<HechoOutputDTO> navegacionColeccion(FiltroInputDTO filtroInputDTO, ModoNavegacion modoNavegacion, String handle);
    List<HechoOutputDTO> obtenerHechosDeColeccion(String handle);
    Mono<Void> refrescarColecciones(Hecho hecho);
}
