package ar.utn.dssi.Agregador.models.entities.solicitud;

import ar.utn.dssi.Agregador.error.SolicitudDescripcionMuyCorta;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class SolicitudDeEliminacionFactory {
  @Value("${caracteresMinimosSolicitud}")
  private Integer caracteresMinimos;

  public SolicitudDeEliminacion crear(Hecho hecho, String descripcion) {
    SolicitudDeEliminacion solicitudDeEliminacion = new SolicitudDeEliminacion();
    validarDescripcion(descripcion);
    solicitudDeEliminacion.setHecho(hecho);
    solicitudDeEliminacion.setDescripcion(descripcion);
    solicitudDeEliminacion.setFechaDeCreacion(LocalDateTime.now());

    return solicitudDeEliminacion;
  }

  private void validarDescripcion(String descripcion) {
    if (descripcion == null || descripcion.trim().isEmpty()) {
      throw new IllegalArgumentException("La descripción no puede estar vacía.");
    }
    if (descripcion.length() < caracteresMinimos) {
      throw new SolicitudDescripcionMuyCorta("La descripción no puede tener menos de 500 caracteres.");
    }
  }
}
