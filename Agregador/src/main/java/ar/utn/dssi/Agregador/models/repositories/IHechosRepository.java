package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IHechosRepository extends JpaRepository<Hecho, Long> {
  List<Hecho> findByIdEnFuenteAndFuente(Long idOrigen, Fuente fuente);

  @Query("SELECT h FROM Hecho h " +
          "WHERE (:fechaReporteDesde IS NULL OR h.fechaCarga >= :fechaReporteDesde) " +
          "AND (:fechaReporteHasta IS NULL OR h.fechaCarga <= :fechaReporteHasta) " +
          "AND (:fechaAcontecimientoDesde IS NULL OR h.fechaAcontecimiento >= :fechaAcontecimientoDesde) " +
          "AND (:fechaAcontecimientoHasta IS NULL OR h.fechaAcontecimiento <= :fechaAcontecimientoHasta) " +
          "AND (:fuenteId IS NULL OR h.fuente.id = :fuenteId) " +
          "AND ( (:latitud IS NULL AND :longitud IS NULL) " +
          "      OR (h.ubicacion.latitud = :latitud AND h.ubicacion.longitud = :longitud) )")
  List<Hecho> filtrarHechos(
          @Param("fechaReporteDesde") LocalDateTime fechaReporteDesde,
          @Param("fechaReporteHasta") LocalDateTime fechaReporteHasta,
          @Param("fechaAcontecimientoDesde") LocalDateTime fechaAcontecimientoDesde,
          @Param("fechaAcontecimientoHasta") LocalDateTime fechaAcontecimientoHasta,
          @Param("latitud") Double latitud,
          @Param("longitud") Double longitud,
          @Param("fuenteId") Long fuenteId
  );


}