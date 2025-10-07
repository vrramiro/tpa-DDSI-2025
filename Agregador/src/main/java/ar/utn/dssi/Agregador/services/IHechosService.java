package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IHechosService {
    //CRUD.
    List<HechoOutputDTO> obtenerHechos(LocalDateTime fechaReporteDesde, LocalDateTime fechaReporteHasta, LocalDateTime fechaAcontecimientoDesde, LocalDateTime fechaAcontecimientoHasta, String ciudad, String provincia, Long fuenteId);
    HechoOutputDTO obtenerHechoPorId(Long idHecho);
    void eliminarHecho(Long IDHecho);

    //AUX
     void importarNuevosHechos();


}
