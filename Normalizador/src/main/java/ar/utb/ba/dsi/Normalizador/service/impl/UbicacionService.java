package ar.utb.ba.dsi.Normalizador.service.impl;

import ar.utb.ba.dsi.Normalizador.dto.output.UbicacionOutputDTO;
import ar.utb.ba.dsi.Normalizador.mappers.MapperDeUbicacion;
import ar.utb.ba.dsi.Normalizador.models.entities.AdapterUbicacion.IUbicacionAdapter;
import ar.utb.ba.dsi.Normalizador.models.entities.Ubicacion;
import ar.utb.ba.dsi.Normalizador.models.entities.errores.HechoNoEcontrado;
import ar.utb.ba.dsi.Normalizador.service.IUbicacionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UbicacionService implements IUbicacionService {
  private final IUbicacionAdapter adapter;

  public UbicacionService(@Qualifier("georefAdapter") IUbicacionAdapter adapter) {
    this.adapter = adapter;
  }

  @Override
  public Ubicacion obtenerUbicacion(Double latitud, Double longitud) {
    Ubicacion ubicacion = adapter.obtenerUbicacionDeAPI(latitud, longitud).block();

    if (ubicacion == null) {
      throw new HechoNoEcontrado("La ubicacion es nula.");
    }

    if (ubicacion.getCiudad() == null && ubicacion.getProvincia() == null) {
      System.out.println("La ciudad y la provincia son nulas");
    }

    return ubicacion;
  }

  @Override
  public UbicacionOutputDTO obtenerUbicacionOutPut(Double latitud, Double longitud) {
    try {
      return MapperDeUbicacion.ubicacionOutputDTO(this.obtenerUbicacion(latitud, longitud));
    } catch (Exception e) {
      throw new RuntimeException("Error mapeando a DTO la ubicacion", e);
    }
  }
}
