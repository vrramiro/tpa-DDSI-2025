package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.solicitud.EstadoDeSolicitud;
import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEliminacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ISolicitudDeEliminacionRepository extends JpaRepository<SolicitudDeEliminacion, Long> {
  @Query("SELECT s " +
      "FROM SolicitudDeEliminacion s " +
      "WHERE (:estado IS NULL OR s.estadoDeSolicitud = :estado) " +
      "AND (:spam IS NULL OR s.esSpam = :spam)")
  List<SolicitudDeEliminacion> findByFiltro(@Param("estado") EstadoDeSolicitud estadoDeSolicitud, @Param("spam") Boolean spam);
}
