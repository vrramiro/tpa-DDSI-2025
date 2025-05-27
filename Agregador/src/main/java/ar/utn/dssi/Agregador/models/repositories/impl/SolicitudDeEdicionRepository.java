package src.main.java.ar.utn.dssi.Agregador.models.repositories.impl;

import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEdicion;
import ar.utn.dssi.Agregador.models.repositories.ISolicitudDeEliminacionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SolicitudDeEdicionRepository implements ISolicitudDeEdicionRepository {
  private List<SolicitudDeEdicion> solicitudes;

  @Override
  public SolicitudDeEliminacion findById(Long id){
    return this.solicitudes.stream().filter(solicitud -> solicitud.getIdSolicitud().equals(id)).findFirst().orElse(null);
  }

  @Override
  public void save(SolicitudDeEliminacion solicitud){
    solicitud.setIdSolicitud((long)this.solicitudes.size());
    this.solicitudes.add(solicitud);
  }

  @Override
  public void update(SolicitudDeEliminacion solicitud){
    this.solicitudes.remove(findById(solicitud.getIdSolicitud()));
    this.solicitudes.add(solicitud);
  }

  @Override
  public List<SolicitudDeEliminacion> findAll(){
    return this.solicitudes;
  }

}
