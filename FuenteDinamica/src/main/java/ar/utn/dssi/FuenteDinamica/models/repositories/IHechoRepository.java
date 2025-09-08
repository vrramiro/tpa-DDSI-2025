package ar.utn.dssi.FuenteDinamica.models.repositories;

import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHechoRepository extends JpaRepository<Hecho, Long> {}
