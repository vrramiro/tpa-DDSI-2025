package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IColeccionRepository extends JpaRepository<Coleccion, String> {
  Coleccion findByHandle(String handle);

  void update(Coleccion coleccion);

}
