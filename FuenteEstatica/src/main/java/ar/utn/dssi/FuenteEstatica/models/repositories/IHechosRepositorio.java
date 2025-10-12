package ar.utn.dssi.FuenteEstatica.models.repositories;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface IHechosRepositorio extends JpaRepository<Hecho, Long> {
  @Query("SELECT h FROM Hecho h " +
      "WHERE (:fechaLimite IS NULL) " +
      "OR (h.fechaCarga >= :fechaLimite )")
  List<Hecho> findHechosByFechaLimite(@Param("fechaLimite") LocalDateTime fechaLimite);
}

