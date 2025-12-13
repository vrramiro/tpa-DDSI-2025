package ar.utn.dssi.Estadisticas.services.impl;

import ar.utn.dssi.Estadisticas.models.entities.adapters.agregador.IAgregadorAdapter;
import ar.utn.dssi.Estadisticas.models.entities.adapters.normalizador.INormalizadorAdapter;
import ar.utn.dssi.Estadisticas.models.entities.data.ContextoDeCalculo;
import ar.utn.dssi.Estadisticas.services.IContextoDeCalculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContextoDeCalculoService implements IContextoDeCalculoService {
  @Autowired
  private final IAgregadorAdapter agregadorAdapter;

  @Autowired
  private final INormalizadorAdapter normalizadorAdapter;

  @Override
  public ContextoDeCalculo obtenerContextoDeCalculo() {
    ContextoDeCalculo contextoDeCalculo = new ContextoDeCalculo();

    contextoDeCalculo.setCategorias(normalizadorAdapter.obtenerCategorias().block());
    contextoDeCalculo.setHechos(agregadorAdapter.obtenerHechos());
    contextoDeCalculo.setColecciones(agregadorAdapter.obtenerColecciones());
    contextoDeCalculo.setSolicitudDeEliminacion(agregadorAdapter.obtenerSolicitudesSpam());

    return contextoDeCalculo;
  }
}
