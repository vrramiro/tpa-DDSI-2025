package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.solicitud.EstadoDeSolicitud;
import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEdicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ISolicitudDeEdicionRepository extends JpaRepository<SolicitudDeEdicion, Long> {
    List<SolicitudDeEdicion> findByEstado(EstadoDeSolicitud estado);
}