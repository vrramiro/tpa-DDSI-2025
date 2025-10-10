package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IColeccionRepository extends JpaRepository<Coleccion, String> {
  Optional<Coleccion> findColeccionByHandle(String handle);
  List<Coleccion> findColeccionByActualizada(Boolean actualizada);
  Optional<Coleccion> findColeccionByTitulo(String titulo);
  @Query("SELECT h FROM Coleccion c JOIN c.hechos h " +
      "WHERE c.handle = :handler " +
      "AND (:fechaReporteDesde IS NULL OR h.fechaCarga >= :fechaReporteDesde) " +
      "AND (:fechaReporteHasta IS NULL OR h.fechaCarga <= :fechaReporteHasta) " +
      "AND (:fechaAcontecimientoDesde IS NULL OR h.fechaAcontecimiento >= :fechaAcontecimientoDesde) " +
      "AND (:fechaAcontecimientoHasta IS NULL OR h.fechaAcontecimiento <= :fechaAcontecimientoHasta) " +
      "AND (:fuenteId IS NULL OR h.fuente.id = :fuenteId) " +
      "AND (:ciudad IS NULL OR h.ubicacion.ciudad = :ciudad) " +
      "AND (:provincia IS NULL OR h.ubicacion.provincia = :provincia) "
      )
  List<Hecho> filtrarHechosDeColeccion(
      @Param("handler") String handler,
      @Param("fechaReporteDesde") LocalDate fechaReporteDesde,
      @Param("fechaReporteHasta") LocalDate fechaReporteHasta,
      @Param("fechaAcontecimientoDesde") LocalDate fechaAcontecimientoDesde,
      @Param("fechaAcontecimientoHasta") LocalDate fechaAcontecimientoHasta,
      @Param("ciudad") String ciudad,
      @Param("provincia") String provincia,
      @Param("fuenteId") Long fuenteId
  );

}
