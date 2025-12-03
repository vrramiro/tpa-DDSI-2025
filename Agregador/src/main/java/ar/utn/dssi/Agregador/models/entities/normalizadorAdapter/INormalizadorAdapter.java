package ar.utn.dssi.Agregador.models.entities.normalizadorAdapter;

import ar.utn.dssi.Agregador.models.entities.Categoria;
import java.util.Optional;

public interface INormalizadorAdapter {
    Optional<Categoria> obtenerCategoriaPorId(Long idCategoria);
}