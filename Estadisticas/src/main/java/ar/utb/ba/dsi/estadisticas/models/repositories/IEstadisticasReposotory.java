package ar.utb.ba.dsi.estadisticas.models.repositories;

import ar.utb.ba.dsi.estadisticas.models.entities.Estadistica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstadisticasReposotory extends JpaRepository<Estadistica, Long> {
}
