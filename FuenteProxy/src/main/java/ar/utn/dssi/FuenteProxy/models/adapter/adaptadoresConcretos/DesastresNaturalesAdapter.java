package ar.utn.dssi.FuenteProxy.models.adapter.adaptadoresConcretos;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.adapter.IServicioExternoAdapter;
import ar.utn.dssi.FuenteProxy.models.adapter.adaptados.DesastresNaturales;
import ar.utn.dssi.FuenteProxy.models.entities.Categoria;
import ar.utn.dssi.FuenteProxy.models.entities.Origen;
import ar.utn.dssi.FuenteProxy.models.entities.Ubicacion;
import java.util.List;

public class DesastresNaturalesAdapter implements IServicioExternoAdapter {
  private DesastresNaturales desastresNaturalesAdaptado;

  public DesastresNaturalesAdapter() {
    this.desastresNaturalesAdaptado = new DesastresNaturales();
  }

  public List<HechoOutputDTO> obtenerHechos(){
    return desastresNaturalesAdaptado
        .obtenerHechosDeAPI()
        .stream()
        .map(hechoObtenido -> {
          HechoOutputDTO dto = new HechoOutputDTO();

          Ubicacion ubicacion = new Ubicacion();
          ubicacion.setLatitud(hechoObtenido.getLatitud());
          ubicacion.setLongitud(hechoObtenido.getLongitud());

          Categoria categoria = new Categoria();
          categoria.setNombre(hechoObtenido.getCategoria());

          dto.setTitulo(hechoObtenido.getTitulo());
          dto.setDescripcion(hechoObtenido.getDescripcion());
          dto.setCategoria(categoria);
          dto.setUbicacion(ubicacion);
          dto.setFechaAcontecimiento(hechoObtenido.getFecha_hecho());
          dto.setFechaCarga(hechoObtenido.getCreated_at());
          dto.setOrigen(Origen.FUENTE_PROXY);

          return dto;
        })
        .toList();
  }
}
