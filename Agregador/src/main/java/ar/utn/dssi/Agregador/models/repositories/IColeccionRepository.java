package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface IColeccionRepository extends JpaRepository<Coleccion, String> {
  Optional<Coleccion> findColeccionByHandle(String handle);
  List<Coleccion> findColeccionByActualizada(Boolean actualizada);
}
