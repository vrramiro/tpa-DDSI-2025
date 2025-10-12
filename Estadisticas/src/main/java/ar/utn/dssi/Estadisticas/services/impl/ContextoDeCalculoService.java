package ar.utn.dssi.Estadisticas.services.impl;

import ar.utn.dssi.Estadisticas.models.entities.adapters.agregador.IAgregadorAdapter;
import ar.utn.dssi.Estadisticas.models.entities.adapters.normalizador.INormalizadorAdapter;
import ar.utn.dssi.Estadisticas.models.entities.data.ContextoDeCalculo;
import ar.utn.dssi.Estadisticas.services.IContextoDeCalculoService;
import org.springframework.stereotype.Service;

@Service
public class ContextoDeCalculoService implements IContextoDeCalculoService {
  private final IAgregadorAdapter agregadorAdapter;
  private final INormalizadorAdapter normalizadorAdapter;

  public ContextoDeCalculoService(INormalizadorAdapter normalizadorAdapter, IAgregadorAdapter agregadorAdapter) {
    this.agregadorAdapter = agregadorAdapter;
    this.normalizadorAdapter = normalizadorAdapter;
  }

  @Override
  public ContextoDeCalculo obtenerContextoDeCalculo() {
    ContextoDeCalculo contextoDeCalculo = new ContextoDeCalculo();

    contextoDeCalculo.setCategorias(normalizadorAdapter.obtenerCategorias());
    contextoDeCalculo.setHechos(agregadorAdapter.obtenerHechos());
    contextoDeCalculo.setColecciones(agregadorAdapter.obtenerColecciones());

    return contextoDeCalculo;
  }
}
