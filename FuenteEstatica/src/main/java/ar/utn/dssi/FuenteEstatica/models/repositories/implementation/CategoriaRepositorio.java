package ar.utn.dssi.FuenteEstatica.models.repositories.implementation;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Categoria;
import ar.utn.dssi.FuenteEstatica.models.repositories.ICategoriaRepositorio;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoriaRepositorio implements ICategoriaRepositorio {
    private List<Categoria> categorias;

    public CategoriaRepositorio() {
        this.categorias = new ArrayList<>();
    }

    @Override
    public Categoria findById(Long id) {
        return this.categorias.stream().filter(categoria -> categoria.getIdCategoria().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void save(Categoria categoria) {
        categoria.setIdCategoria((long) this.categorias.size());
        this.categorias.add(categoria);
    }

    @Override
    public List<Categoria> findall() {return this.categorias;}
}