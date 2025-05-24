package ar.utn.dssi.Agregador.servicios;

import ar.utn.dssi.Agregador.modelos.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.modelos.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.modelos.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Coleccion;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IColeccionService {
    List<ColeccionOutputDTO> obtenerColecciones();
    void crearColeccion(ColeccionInputDTO coleccionInputDTO);
    void guardarEnColeccion(Hecho hecho);
    List<HechoOutputDTO> obtenerHechosDeColeccion (String handle);
    ColeccionOutputDTO coleccionOutputDTO(Coleccion colecion);
}
