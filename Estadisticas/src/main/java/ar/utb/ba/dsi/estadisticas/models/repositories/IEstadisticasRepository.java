package ar.utb.ba.dsi.estadisticas.models.repositories;

import ar.utb.ba.dsi.estadisticas.models.entities.Estadistica;
import ar.utb.ba.dsi.estadisticas.models.entities.TipoEstadistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IEstadisticasRepository extends JpaRepository<Estadistica, Long> {
    Estadistica findEstadisticaByCategoriaIdAndTipoAndFechaDeCalculoIsAfter(Long idCategoria, TipoEstadistica tipo, LocalDateTime fechaDeCalculo);
    Estadistica findEStadisticaByColeccionIdAndTipoAndFechaDeCalculoIsAfter(Long idColeccion, TipoEstadistica tipo, LocalDateTime fechaDeCalculo);
    Estadistica findEstadisticaByTipoAndFechaDeCalculoIsAfter(TipoEstadistica tipo, LocalDateTime fechaDeCalculo);
}
