package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
          "AND (:navegacionCurada IS FALSE OR EXISTS (SELECT c FROM h.consensosDados c WHERE c <> ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.TipoConsenso.NINGUNO )) " +
          "AND (:fechaReporteHasta IS NULL OR h.fechaCarga <= :fechaReporteHasta) " +
          "AND (:fechaAcontecimientoDesde IS NULL OR h.fechaAcontecimiento >= :fechaAcontecimientoDesde) " +
          "AND (:fechaAcontecimientoHasta IS NULL OR h.fechaAcontecimiento <= :fechaAcontecimientoHasta) " +
          "AND (:provincia IS NULL OR h.ubicacion.provincia = :provincia) " +
          "AND (:idCategoria IS NULL OR h.categoria.id = :idCategoria) " +
          "AND (:latMin IS NULL OR h.ubicacion.latitud >= :latMin) " +
          "AND (:latMax IS NULL OR h.ubicacion.latitud <= :latMax) " +
          "AND (:lonMin IS NULL OR h.ubicacion.longitud >= :lonMin) " +
          "AND (:lonMax IS NULL OR h.ubicacion.longitud <= :lonMax) " +
          "AND h.visible = true")

  List<Hecho> findHechosByVisibleTrueAndFiltrados(
          @Param("navegacionCurada") boolean navegacionCurada,
          @Param("fechaReporteDesde") LocalDateTime fechaReporteDesde,
          @Param("fechaReporteHasta") LocalDateTime fechaReporteHasta,
          @Param("fechaAcontecimientoDesde") LocalDateTime fechaAcontecimientoDesde,
          @Param("fechaAcontecimientoHasta") LocalDateTime fechaAcontecimientoHasta,
          @Param("idCategoria") Long idCategoria,
          @Param("provincia") String provincia,
          @Param("latMin") Double latMin,
          @Param("latMax") Double latMax,
          @Param("lonMin") Double lonMin,
          @Param("lonMax") Double lonMax
  );

  Optional<Hecho> findHechoByIdAndVisible(Long idHecho, boolean visible);

  List<Hecho> findByVisible(boolean visible);

  Page<Hecho> findByVisibleTrue(Pageable pageable);

  List<Hecho> findByAutor(String autor);

  @Query("SELECT h FROM Hecho h WHERE h.visible is true ORDER BY h.fechaCarga DESC ")
  List<Hecho> findHechosRecientes(Pageable pageable);
}