package ar.utn.dssi.Estadisticas.services.impl;

import ar.utn.dssi.Estadisticas.models.entities.adapters.agregador.IAgregadorAdapter;
import ar.utn.dssi.Estadisticas.models.entities.adapters.normalizador.INormalizadorAdapter;
import ar.utn.dssi.Estadisticas.models.entities.data.ContextoDeCalculo;
import ar.utn.dssi.Estadisticas.services.IContextoDeCalculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContextoDeCalculoService implements IContextoDeCalculoService {
  private final IAgregadorAdapter agregadorAdapter;
  private final INormalizadorAdapter normalizadorAdapter;

  @Override
  public ContextoDeCalculo obtenerContextoDeCalculo() {
    ContextoDeCalculo contextoDeCalculo = new ContextoDeCalculo();

    contextoDeCalculo.setCategorias(normalizadorAdapter.obtenerCategorias());
    contextoDeCalculo.setHechos(agregadorAdapter.obtenerHechos());
    contextoDeCalculo.setColecciones(agregadorAdapter.obtenerColecciones());

    return contextoDeCalculo;
  }
}
