package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.Optional;

public interface IColeccionRepository extends JpaRepository<Coleccion, String> {
  Optional<Coleccion> findColeccionByHandle(String handle);

  Page<Coleccion> findColeccionByActualizada(Boolean actualizada, Pageable pageable);

  Optional<Coleccion> findColeccionByTitulo(String titulo);

  @Query("SELECT h FROM Coleccion c JOIN c.hechos h " +
      "WHERE c.handle = :handler " +
      "AND (:fechaReporteDesde IS NULL OR h.fechaCarga >= :fechaReporteDesde) " +
      "AND (:fechaReporteHasta IS NULL OR h.fechaCarga <= :fechaReporteHasta) " +
      "AND (:fechaAcontecimientoDesde IS NULL OR h.fechaAcontecimiento >= :fechaAcontecimientoDesde) " +
      "AND (:fechaAcontecimientoHasta IS NULL OR h.fechaAcontecimiento <= :fechaAcontecimientoHasta) " +
      "AND (:ciudad IS NULL OR h.ubicacion.ciudad = :ciudad) " +
      "AND (:provincia IS NULL OR h.ubicacion.provincia = :provincia) " +
      "AND h.visible = true"
  )
  Page<Hecho> findHechosByFiltrado(
      @Param("handler") String handler,
      @Param("fechaReporteDesde") LocalDateTime fechaReporteDesde,
      @Param("fechaReporteHasta") LocalDateTime fechaReporteHasta,
      @Param("fechaAcontecimientoDesde") LocalDateTime fechaAcontecimientoDesde,
      @Param("fechaAcontecimientoHasta") LocalDateTime fechaAcontecimientoHasta,
      @Param("ciudad") String ciudad,
      @Param("provincia") String provincia,
      Pageable pageable
  );

  @Query("SELECT h FROM Coleccion c JOIN c.hechos h " +
      "WHERE c.handle = :handler " +
      "AND (c.consenso MEMBER OF h.consensosDados) " +
      "AND (:fechaReporteDesde IS NULL OR h.fechaCarga >= :fechaReporteDesde) " +
      "AND (:fechaReporteHasta IS NULL OR h.fechaCarga <= :fechaReporteHasta) " +
      "AND (:fechaAcontecimientoDesde IS NULL OR h.fechaAcontecimiento >= :fechaAcontecimientoDesde) " +
      "AND (:fechaAcontecimientoHasta IS NULL OR h.fechaAcontecimiento <= :fechaAcontecimientoHasta) " +
      "AND (:ciudad IS NULL OR h.ubicacion.ciudad = :ciudad) " +
      "AND (:provincia IS NULL OR h.ubicacion.provincia = :provincia) " +
      "AND h.visible = true"
  )
  Page<Hecho> findHechosByFiltradoCurado(
      @Param("handler") String handler,
      @Param("fechaReporteDesde") LocalDateTime fechaReporteDesde,
      @Param("fechaReporteHasta") LocalDateTime fechaReporteHasta,
      @Param("fechaAcontecimientoDesde") LocalDateTime fechaAcontecimientoDesde,
      @Param("fechaAcontecimientoHasta") LocalDateTime fechaAcontecimientoHasta,
      @Param("ciudad") String ciudad,
      @Param("provincia") String provincia,
      Pageable pageable
  );

  List<Coleccion> findByActualizadaTrue();
}
