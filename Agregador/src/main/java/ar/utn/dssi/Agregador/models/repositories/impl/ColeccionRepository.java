package ar.utn.dssi.Agregador.models.repositories.impl;

import ar.utn.dssi.Agregador.models.entities.content.Coleccion;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ColeccionRepository implements IColeccionRepository {
    private List<Coleccion> colecciones;

    public ColeccionRepository() {
        this.colecciones = new ArrayList<>();
    }

    //OPERACIONES CRUD
    @Override
    public Coleccion save(Coleccion coleccion) {    //GUARDA (CREATE)
        this.colecciones.add(coleccion);
        return coleccion;
    }

    @Override
    public List<Coleccion> findall() {
        return this.colecciones;
    }   //READ ALL COLECCIONES


    @Override
    public void update(Coleccion coleccionActualizada) {     //UPDATE
        for (int i = 0; i < colecciones.size(); i++) {
            Coleccion actual = colecciones.get(i);
            if (actual.getHandle().equals(coleccionActualizada.getHandle())) {
                colecciones.set(i, coleccionActualizada);
            }
        }
    }

    @Override
    public void delete(Coleccion coleccion) {       //DELETE
        colecciones.remove(coleccion);
    }

    //BUSCAR COLECCION POR SU HANDLE
    @Override
    public Coleccion findByHandle(String handle) {
        return this.colecciones.stream().filter(coleccion -> coleccion.getHandle().equals(handle)).findFirst().orElse(null);
    }
}



