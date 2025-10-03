package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;

import java.util.List;

public interface IHechosService {
    //CRUD
    List<HechoOutputDTO> obtenerHechos();
    HechoOutputDTO obtenerHechoPorId(Long idHecho);
    void eliminarHecho(Long IDHecho);

    //AUX
     void importarNuevosHechos();
}
