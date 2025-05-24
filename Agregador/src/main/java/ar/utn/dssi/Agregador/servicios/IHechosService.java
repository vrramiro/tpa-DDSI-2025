package ar.utn.dssi.Agregador.servicios;

import ar.utn.dssi.Agregador.modelos.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.modelos.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Origen;

import java.util.List;

public interface IHechosService {
    void crearHecho(HechoInputDTO hecho, Origen origen);
    HechoOutputDTO hechoOutputDTO(Hecho hecho);
    void importarHechos(List<HechoInputDTO> hechos);
}
