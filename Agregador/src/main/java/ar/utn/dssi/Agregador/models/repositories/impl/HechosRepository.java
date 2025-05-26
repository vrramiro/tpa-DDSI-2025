package ar.utn.dssi.Agregador.models.repositories.impl;

import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;

import java.util.ArrayList;
import java.util.List;

public class HechosRepository implements IHechosRepository {
    private List<Hecho> hechos;

    public HechosRepository() {
        this.hechos = new ArrayList<>();
    }

    @Override
    public Hecho findById(Long id) {
        return this.hechos.stream().filter(hecho -> hecho.getIdHecho().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Hecho save(Hecho hecho) {
        hecho.setIdHecho((long) this.hechos.size());
        this.hechos.add(hecho);

        return hecho;
    }

    @Override
    public List<Hecho> findall() {
        return this.hechos;
    }

    @Override
    public long obtenerUltimoId() {
        return this.hechos.size()+1;
    }

    @Override
    public void update(Hecho hecho) {
        hechos.remove(findById(hecho.getIdHecho()));
        hechos.add(hecho);
    }
}



