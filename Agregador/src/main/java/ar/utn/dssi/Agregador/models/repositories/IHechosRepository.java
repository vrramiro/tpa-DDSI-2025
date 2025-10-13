package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IHechosRepository extends JpaRepository<Hecho, Long> {
  @Query("SELECT h FROM Hecho h " +
      "WHERE (:fechaReporteDesde IS NULL OR h.fechaCarga >= :fechaReporteDesde) " +
      "AND (:fechaReporteHasta IS NULL OR h.fechaCarga <= :fechaReporteHasta) " +
      "AND (:fechaAcontecimientoDesde IS NULL OR h.fechaAcontecimiento >= :fechaAcontecimientoDesde) " +
      "AND (:fechaAcontecimientoHasta IS NULL OR h.fechaAcontecimiento <= :fechaAcontecimientoHasta) " +
      "AND (:ciudad IS NULL OR h.ubicacion.ciudad = :ciudad) " +
      "AND (:provincia IS NULL OR h.ubicacion.provincia = :provincia) " +
      "AND h.visible = true")
  List<Hecho> findHechosByVisibleTrueAndFiltrados(
      @Param("fechaReporteDesde") LocalDateTime fechaReporteDesde,
      @Param("fechaReporteHasta") LocalDateTime fechaReporteHasta,
      @Param("fechaAcontecimientoDesde") LocalDateTime fechaAcontecimientoDesde,
      @Param("fechaAcontecimientoHasta") LocalDateTime fechaAcontecimientoHasta,
      @Param("ciudad") String ciudad,
      @Param("provincia") String provincia
  );

  Optional<Hecho> findHechoByIdAndVisible(Long idHecho, boolean visible);
}