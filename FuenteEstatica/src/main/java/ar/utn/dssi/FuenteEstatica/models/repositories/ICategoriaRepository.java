package ar.utn.dssi.FuenteEstatica.models.repositories;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Categoria;
import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
}
