package ar.utn.dssi.Estadisticas.models.repositories;

import ar.utn.dssi.Estadisticas.models.entities.Estadistica;
import ar.utn.dssi.Estadisticas.models.entities.TipoEstadistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstadisticasRepository extends JpaRepository<Estadistica, Long> {
  Estadistica findTopByCategoriaIdAndTipoOrderByFechaDeCalculoDesc(Long idCategoria, TipoEstadistica tipo);

  Estadistica findTopByColeccionIdAndTipoOrderByFechaDeCalculoDesc(Long coleccionId, TipoEstadistica tipo);

  Estadistica findTopByTipoOrderByFechaDeCalculoDesc(TipoEstadistica tipo);
}
