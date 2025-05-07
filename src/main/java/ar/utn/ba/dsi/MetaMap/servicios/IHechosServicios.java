package ar.utn.ba.dsi.MetaMap.servicios;

import ar.utn.ba.dsi.MetaMap.modelos.DTOs.input.HechoInputDTO;
import ar.utn.ba.dsi.MetaMap.modelos.DTOs.output.HechoOutputDTO;

import java.util.List;

public interface IHechosServicios {
    List<HechoOutputDTO> buscarHechos();
    HechoOutputDTO buscarHechoPorId(Long id);
    HechoOutputDTO crearHecho(HechoInputDTO hechoInputDTO);
    HechoOutputDTO actualizarHecho(Long id, HechoInputDTO hechoInputDTO);
    // void eliminarHecho(Long id);  TODO: AGREGAR VISIBILIDAD AL DTO PARA PODER MODIFICAR LA MISMA

}
