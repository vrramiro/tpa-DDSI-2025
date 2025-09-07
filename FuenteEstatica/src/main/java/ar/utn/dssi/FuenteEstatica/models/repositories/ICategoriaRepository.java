package ar.utn.dssi.FuenteEstatica.models.repositories;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<Hecho, Long> {
}
