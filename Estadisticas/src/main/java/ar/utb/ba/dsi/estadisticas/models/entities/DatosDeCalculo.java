package ar.utb.ba.dsi.estadisticas.models.entities;

import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.CategoriaInputDTO;
import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.ColeccionInputDTO;
import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.HechoInputDTO;
import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.SolicitudDeEliminacionInputDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DatosDeCalculo {
  // Los campos son nulleables, cada calculador usa los que necesita
  private List<Hecho> hechos;
  private List<Coleccion> colecciones;
  private List<SolicitudDeEliminacion> solicitudDeEliminacion;
  private List<Categoria> categorias;
}
