package src.main.java.ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEliminacion;
import org.springframework.stereotype.Repository;
import src.main.java.ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEdicion;

import java.util.List;

@Repository
public interface ISolicitudDeEdicionRepository {
    SolicitudDeEdicion findById(Long id);
    void save(SolicitudDeEdicion solicitud);
    void update (SolicitudDeEdicion solicitud);
    List<SolicitudDeEdicion> findAll();
}
