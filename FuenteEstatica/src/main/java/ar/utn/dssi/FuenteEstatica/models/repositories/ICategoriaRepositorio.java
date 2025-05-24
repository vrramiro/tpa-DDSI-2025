package ar.utn.dssi.FuenteEstatica.models.repositories;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Categoria;
import java.util.List;

public interface ICategoriaRepositorio {
    public Categoria findById(Long id);
    public void save(Categoria categoria);
    public List<Categoria> findall();
}