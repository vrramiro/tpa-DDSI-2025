package ar.utn.ba.dsi.MetaMap.prueba.modelos.repositorios.impl;

import ar.utn.ba.dsi.MetaMap.prueba.modelos.entidades.contenido.Hecho;
import ar.utn.ba.dsi.MetaMap.prueba.modelos.repositorios.IHechosRepositorio;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HechosRepositorio implements IHechosRepositorio{
    private List<Hecho> hechos;

    public HechosRepositorio() {this.hechos = new ArrayList<>();}

    @Override
    public Hecho buscarHechoPorId(Long id) {
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
