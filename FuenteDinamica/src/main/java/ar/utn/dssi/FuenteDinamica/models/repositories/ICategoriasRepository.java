package ar.utn.dssi.FuenteDinamica.models.repositories;

import ar.utn.dssi.FuenteDinamica.models.entities.Categoria;
import java.util.List;

public interface ICategoriasRepository {
  public Categoria findById(Long id);
  public void save(Categoria categoria);
  public List<Categoria> findall();
}
