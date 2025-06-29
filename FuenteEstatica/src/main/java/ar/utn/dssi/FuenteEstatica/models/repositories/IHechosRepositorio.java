package ar.utn.dssi.FuenteEstatica.models.repositories;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;

import java.util.List;
import java.util.Optional;

public interface IHechosRepositorio {
    public void save(List<Hecho> hechos);
    public List<Hecho> findAll();
    public void update(Hecho hechos);
    public Optional<Hecho> findById(Long id);
}
