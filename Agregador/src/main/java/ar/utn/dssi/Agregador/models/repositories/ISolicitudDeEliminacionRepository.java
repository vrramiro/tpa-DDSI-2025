package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.solicitud.EstadoDeSolicitud;
import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEliminacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ISolicitudDeEliminacionRepository extends JpaRepository<SolicitudDeEliminacion, Long> {
    List<SolicitudDeEliminacion> findByEsSpam (Boolean esSpam);
    List<SolicitudDeEliminacion> findByEstadoDeSolicitud (EstadoDeSolicitud estado);
}
