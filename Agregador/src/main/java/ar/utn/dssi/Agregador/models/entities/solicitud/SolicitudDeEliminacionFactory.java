package ar.utn.dssi.Agregador.models.entities.solicitud;

import ar.utn.dssi.Agregador.error.SolicitudDescripcionMuyCorta;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class SolicitudDeEliminacionFactory {

  public SolicitudDeEliminacion crear(Hecho hecho, String descripcion) {
    SolicitudDeEliminacion solicitudDeEliminacion = new SolicitudDeEliminacion();
    solicitudDeEliminacion.setHecho(hecho);
    solicitudDeEliminacion.setDescripcion(descripcion);
    solicitudDeEliminacion.setFechaDeCreacion(LocalDateTime.now());

    return solicitudDeEliminacion;
  }
}
