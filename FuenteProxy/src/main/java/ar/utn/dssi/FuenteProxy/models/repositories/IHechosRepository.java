package ar.utn.dssi.FuenteProxy.models.repositories;

import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHechosRepository extends JpaRepository<Hecho, Long> {
}
