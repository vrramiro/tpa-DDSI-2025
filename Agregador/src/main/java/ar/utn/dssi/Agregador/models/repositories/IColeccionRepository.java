package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.content.Coleccion;

import java.util.List;

public interface IColeccionRepository {
    public Coleccion save(Coleccion coleccion);
    public List<Coleccion> findall();
    public Coleccion findByHandle(String handle);
    public void update(Coleccion coleccion);
    void delete(Coleccion coleccion);
}
