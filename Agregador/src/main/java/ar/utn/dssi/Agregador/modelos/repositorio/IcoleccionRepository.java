package ar.utn.dssi.Agregador.modelos.repositorio;

import ar.utn.dssi.Agregador.modelos.entidades.contenido.Coleccion;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;

import java.util.List;

public interface IcoleccionRepository {
    public Coleccion save(Coleccion coleccion);
    public List<Coleccion> findall();
    public Coleccion findByHandle(String handle);
}
