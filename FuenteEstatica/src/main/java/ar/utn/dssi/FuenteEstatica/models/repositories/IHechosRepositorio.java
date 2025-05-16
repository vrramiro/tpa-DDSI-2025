package ar.utn.dssi.FuenteEstatica.models.repositories;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;

import java.util.List;

public interface IHechosRepositorio {
    public void save();
    public List<Hecho> findAll();
}
