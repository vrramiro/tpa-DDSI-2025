package ar.utn.dssi.Estadisticas.models.repositories;

import ar.utn.dssi.Estadisticas.models.entities.Estadistica;
import ar.utn.dssi.Estadisticas.models.entities.TipoEstadistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface IEstadisticasRepository extends JpaRepository<Estadistica, Long> {
  Estadistica findEstadisticaByCategoriaIdAndTipoAndFechaDeCalculoIsAfter(Long idCategoria, TipoEstadistica tipo, LocalDateTime fechaDeCalculo);

  Estadistica findEStadisticaByColeccionIdAndTipoAndFechaDeCalculoIsAfter(Long idColeccion, TipoEstadistica tipo, LocalDateTime fechaDeCalculo);

  Estadistica findEstadisticaByTipoAndFechaDeCalculoIsAfter(TipoEstadistica tipo, LocalDateTime fechaDeCalculo);
}
