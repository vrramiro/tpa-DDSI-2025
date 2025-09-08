package ar.utb.ba.dsi.estadisticas.models.adapters.agregador;

import ar.utb.ba.dsi.estadisticas.models.entities.Coleccion;
import ar.utb.ba.dsi.estadisticas.models.entities.Hecho;
import ar.utb.ba.dsi.estadisticas.models.entities.SolicitudDeEliminacion;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface IAgregadorAdapter {
    public List<Hecho> obtenerHechos();
    public List<Coleccion> obtenerColecciones();
    public List<SolicitudDeEliminacion> obtenerSolicitudes();
}
