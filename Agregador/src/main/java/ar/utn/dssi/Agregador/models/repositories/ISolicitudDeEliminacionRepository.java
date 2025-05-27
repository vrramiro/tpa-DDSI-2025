package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEliminacion;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ISolicitudDeEliminacionRepository {
    SolicitudDeEliminacion findById(Long id);
    void save(SolicitudDeEliminacion solicitud);
    void update (SolicitudDeEliminacion solicitud);
    List<SolicitudDeEliminacion> findAll();
}
