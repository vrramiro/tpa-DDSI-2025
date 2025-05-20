package ar.utn.dssi.Agregador.modelos.repositorio.impl;

import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;
import ar.utn.dssi.Agregador.modelos.repositorio.IHechosRepository;

import java.util.ArrayList;
import java.util.List;

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
    public Hecho save(Hecho hecho) {
        hecho.setId((long) this.hechos.size());
        this.hechos.add(hecho);

        return hecho;
    }

    @Override
    public List<Hecho> findall() {return this.hechos;}

    @Override
    public long obtenerUltimoId() {
        return this.hechos.size()+1;
    }
}

