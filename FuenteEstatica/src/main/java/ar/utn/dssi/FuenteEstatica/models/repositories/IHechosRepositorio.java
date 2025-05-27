package ar.utn.dssi.FuenteEstatica.models.repositories;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;

import java.util.List;

public interface IHechosRepositorio {
    public void save(List<Hecho> hechos);
    public List<Hecho> findAll();
    public void update(Hecho hechos);
    public Hecho findById(Long id);
}
