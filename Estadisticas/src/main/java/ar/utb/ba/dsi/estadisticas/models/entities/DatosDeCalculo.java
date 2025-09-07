package ar.utb.ba.dsi.estadisticas.models.entities;

import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.ColeccionInputDTO;
import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.HechoInputDTO;
import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.SolicitudDeEliminacionInputDTO;
import lombok.Getter;
import java.util.List;

@Getter
public class DatosDeCalculo {
  // Los campos son nulleables, cada calculador usa los que necesita
  private List<HechoInputDTO> hechos;
  private List<ColeccionInputDTO> colecciones;
  private List<SolicitudDeEliminacionInputDTO> solicitudDeEliminacion;
  //TODO lo dejo como input hay que ver si lo pasamos a entidad de dominio que lo mapee aunque sea igual
}
