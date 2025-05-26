package ar.utn.dssi.Agregador.servicios;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Coleccion;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
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
