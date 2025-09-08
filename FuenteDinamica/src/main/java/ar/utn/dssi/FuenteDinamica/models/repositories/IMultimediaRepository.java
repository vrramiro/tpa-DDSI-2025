package ar.utn.dssi.FuenteDinamica.models.repositories;

import ar.utn.dssi.FuenteDinamica.models.entities.ContenidoMultimedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMultimediaRepository extends JpaRepository<ContenidoMultimedia, Long> { }

