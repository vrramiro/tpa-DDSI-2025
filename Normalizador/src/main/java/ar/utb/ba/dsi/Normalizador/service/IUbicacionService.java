package ar.utb.ba.dsi.Normalizador.service;

import ar.utb.ba.dsi.Normalizador.dto.output.UbicacionOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Ubicacion;

public interface IUbicacionService {
  public Ubicacion obtenerUbicacion(Double latitud, Double longitud);

  public UbicacionOutputDTO obtenerUbicacionOutPut(Double latitud, Double longitud);

}
