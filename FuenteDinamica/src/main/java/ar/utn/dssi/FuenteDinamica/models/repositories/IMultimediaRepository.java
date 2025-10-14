package ar.utn.dssi.FuenteDinamica.models.repositories;

import ar.utn.dssi.FuenteDinamica.models.entities.ContenidoMultimedia;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IMultimediaRepository extends JpaRepository<ContenidoMultimedia, Long> {
  List<ContenidoMultimedia> findByHecho(Hecho hecho);
}

