package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IColeccionRepository extends JpaRepository<Coleccion, Long> {
}
