package ar.utn.dssi.FuenteDinamica.models.repositories.viejos.impl;

import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.models.repositories.viejos.IHechosRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HechosRepository implements IHechosRepository {
  private List<Hecho> hechos;

  public HechosRepository() {
    this.hechos = new ArrayList<>();
  }

  @Override
  public Hecho findById(Long id) {
    return this.hechos.stream()
            .filter(hecho -> hecho.getIdHecho().equals(id))
            .findFirst()
            .orElse(null);
  }

  @Override
  public Hecho save(Hecho hecho) {
    this.hechos.add(hecho);

    return hecho;
  }

  @Override
  public List<Hecho> findall() {return this.hechos;}

  @Override
  public long obtenerUltimoId() {
    return this.hechos.size()+1;
  }

  @Override
  public void update(Hecho hecho) {
    hechos.remove(findById(hecho.getIdHecho()));
    hechos.add(hecho);
  }

  @Override
  public void delete(Long id){
    var hecho = findById(id);
    hecho.setVisible(false);
    this.update(hecho);
  }
}