package ar.utn.dssi.FuenteDinamica.models.repositories.viejos.impl;

import ar.utn.dssi.FuenteDinamica.models.entities.Categoria;
import ar.utn.dssi.FuenteDinamica.models.repositories.viejos.ICategoriasRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoriasRepository implements ICategoriasRepository {
  private List<Categoria> categorias;

  public CategoriasRepository() {
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