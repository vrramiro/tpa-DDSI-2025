package ar.utn.dssi.Agregador.models.entities.fuente.impl.fuenteProxy;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoProxy;
import ar.utn.dssi.Agregador.models.mappers.MapperDeHechos;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class FuenteProxy implements ITipoProxy {
  private FuenteProxyConcreta fuenteProxyConcreta;

  public FuenteProxy() {
    this.fuenteProxyConcreta = new FuenteProxyConcreta();
  }

  @Override
  public List<Hecho> hechosNuevos(Fuente fuente) {
    return this.fuenteProxyConcreta.getHechos(fuente.getBaseUrl()).stream().map(MapperDeHechos::hecho).toList();
  }

  @Override
  public List<Hecho> hechosMetamapa(Fuente fuente) {
    return this.fuenteProxyConcreta.getHechosMetamapa(fuente.getBaseUrl()).stream().map(MapperDeHechos::hecho).toList();
  }
}
