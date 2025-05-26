package ar.utn.dssi.Agregador.servicios;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import ar.utn.dssi.Agregador.models.entities.content.Origen;

import java.util.List;

public interface IHechosService {
    HechoOutputDTO hechoOutputDTO(Hecho hecho);
    void importarHechos(List<HechoInputDTO> hechos);
}
