package ar.utn.dssi.Agregador.servicios;

import ar.utn.dssi.Agregador.modelos.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.modelos.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;

import java.util.List;

public interface IHechosService {
    public List<HechoOutputDTO> obtenerHechos();
    public List<HechoOutputDTO> importarHechos();
    public HechoOutputDTO hechoOutputDTO(Hecho hecho);
}
