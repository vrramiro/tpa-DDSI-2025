package ar.utn.dssi.FuenteProxy.models.adapter.adaptadoresConcretos;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.adapter.IServicioExternoAdapter;
import ar.utn.dssi.FuenteProxy.models.adapter.adaptados.DesastresNaturales;
import java.util.List;

public class DesastresNaturalesAdapter implements IServicioExternoAdapter {
  private DesastresNaturales webClientAdaptado;

  public DesastresNaturalesAdapter() {
    this.webClientAdaptado = new DesastresNaturales();
  }

  public List<HechoOutputDTO> obtenerHechos(){
    return List.of();
  }
}
