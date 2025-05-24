package ar.utn.dssi.Agregador.modelos.repositorio.impl;

import ar.utn.dssi.Agregador.modelos.entidades.contenido.Coleccion;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;
import ar.utn.dssi.Agregador.modelos.repositorio.IHechosRepository;
import ar.utn.dssi.Agregador.modelos.repositorio.IcoleccionRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ColeccionRepository implements IcoleccionRepository {
    private List<Coleccion> colecciones;

    public ColeccionRepository() {
        this.colecciones = new ArrayList<>();
    }


    @Override
    public Coleccion save(Coleccion coleccion) {
        this.colecciones.add(coleccion);
        return coleccion;
    }

    @Override
    public List<Coleccion> findall() {
        return this.colecciones;
    }

    @Override
    public Coleccion findByHandle(String handle) {
        return this.colecciones.stream().filter(coleccion -> coleccion.getHandle().equals(handle)).findFirst().orElse(null);
    }
}



