package ar.utn.dssi.FuenteDinamica.models.repositories;

import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IHechoRepository extends JpaRepository<Hecho, Long> {

    @Query("SELECT h FROM Hecho h " +
            "WHERE (:fechaLimite IS NULL) " +
            "OR (h.fechaCarga >= :fechaLimite " +
            "OR (h.fechaEdicion IS NOT NULL AND h.fechaEdicion >= :fechaLimite))")
    List<Hecho> findHechosByFechaLimite(@Param("fechaLimite") LocalDateTime fechaLimite);
}
