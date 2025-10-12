package ar.utn.dssi.Agregador.models.entities.modoNavegacion;

import ar.utn.dssi.Agregador.models.entities.modoNavegacion.impl.NavegacionCurada;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.impl.NavegacionIrrestricta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModoNavegacionFactory {

    private NavegacionCurada navegacionCurada;
    private NavegacionIrrestricta navegacionIrrestricta;

    @Autowired
    public ModoNavegacionFactory(NavegacionCurada navegacionCurada, NavegacionIrrestricta navegacionIrrestricta) {
        this.navegacionCurada = navegacionCurada;
        this.navegacionIrrestricta = navegacionIrrestricta;
    }

    public IModoNavegacion crearDesdeEnum(ModoNavegacion modo) {
        return switch (modo) {
            case NAVEGACIONCURADA -> navegacionCurada;
            case NAVEGACIONIRRESTRICTA -> navegacionIrrestricta;
            default -> throw new IllegalArgumentException("Modo de navegación no soportado: " + modo);
        };
    }
}
