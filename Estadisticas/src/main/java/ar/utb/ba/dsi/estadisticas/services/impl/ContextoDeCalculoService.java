package ar.utb.ba.dsi.estadisticas.services.impl;

import ar.utb.ba.dsi.estadisticas.models.adapters.agregador.IAgregadorAdapter;
import ar.utb.ba.dsi.estadisticas.models.adapters.normalizador.INormalizadorAdapter;
import ar.utb.ba.dsi.estadisticas.models.entities.data.ContextoDeCalculo;
import ar.utb.ba.dsi.estadisticas.services.IContextoDeCalculoService;

public class ContextoDeCalculoService implements IContextoDeCalculoService {
  private IAgregadorAdapter agregadorAdapter;
  private INormalizadorAdapter normalizadorAdapter;

  public ContextoDeCalculoService(IAgregadorAdapter agregadorAdapter, INormalizadorAdapter normalizadorAdapter) {
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
