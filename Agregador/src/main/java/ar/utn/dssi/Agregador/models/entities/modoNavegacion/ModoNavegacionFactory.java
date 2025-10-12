package ar.utn.dssi.Agregador.models.entities.modoNavegacion;

import ar.utn.dssi.Agregador.models.entities.modoNavegacion.impl.NavegacionCurada;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.impl.NavegacionIrrestricta;
import org.springframework.stereotype.Component;

@Component
public class ModoNavegacionFactory {
  public IModoNavegacion modoDeNavegacionFromString(String input) {
    String modoNormalizado = input.trim().toUpperCase();

    try {
      ModoNavegacion modo = ModoNavegacion.valueOf(modoNormalizado);

      return switch (modo) {
        case NAVEGACION_CURADA -> new NavegacionCurada();
        case NAVEGACION_IRRESTRICTA -> new NavegacionIrrestricta();
      };
    } catch (Exception e) {
      throw new IllegalArgumentException("Modo de navegación inválido: " + input, e);
    }
  }
}
