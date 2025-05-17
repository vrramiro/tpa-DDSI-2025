package ar.utn.dssi.FuenteDinamica.models.repositories.impl;

import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.models.repositories.IHechosRepository;
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
    return this.hechos.stream().filter(hecho -> hecho.getId().equals(id)).findFirst().orElse(null);
  }

  @Override
  public void save(Hecho hecho) {
    hecho.setId((long) this.hechos.size());
    this.hechos.add(hecho);
  }

  @Override
  public List<Hecho> findall() {return this.hechos;}
}