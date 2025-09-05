package ar.utn.dssi.FuenteDinamica.models.repositories.viejos;

import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import java.util.List;

public interface IHechosRepository {
  public Hecho findById(Long idHecho);
  public Hecho save(Hecho hecho);
  public List<Hecho> findall();
  public long obtenerUltimoId();
  public void update(Hecho hecho);
  public void delete(Long idHecho);
}