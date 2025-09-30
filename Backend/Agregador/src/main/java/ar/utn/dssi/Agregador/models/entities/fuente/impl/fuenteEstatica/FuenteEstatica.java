package ar.utn.dssi.Agregador.models.entities.fuente.impl.fuenteEstatica;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoFuente;
import ar.utn.dssi.Agregador.models.mappers.MapperDeHechos;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class FuenteEstatica implements ITipoFuente {
  private FuenteEstaticaConcreta fuenteEstaticaConcreta;

  public FuenteEstatica() {
    this.fuenteEstaticaConcreta = new FuenteEstaticaConcreta();
  }

  @Override
  public List<Hecho> hechosNuevos(Fuente fuente) {
    return fuenteEstaticaConcreta.getHechos(fuente.getBaseUrl()).stream().map(MapperDeHechos::hecho).toList();
  }
}
