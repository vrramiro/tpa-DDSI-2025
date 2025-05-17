package ar.utn.dssi.FuenteDinamica.models.repositories;

import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import java.util.List;

public interface IHechosRepository {
  public Hecho findById(Long id);
  public void save(Hecho hecho);
  public List<Hecho> findall();
}