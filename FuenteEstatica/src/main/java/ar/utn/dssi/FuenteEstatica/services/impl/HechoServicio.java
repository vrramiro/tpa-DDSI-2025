package ar.utn.dssi.FuenteEstatica.services.impl;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import ar.utn.dssi.FuenteEstatica.services.IHechoServicio;
import ar.utn.dssi.FuenteEstatica.models.repositories.IHechosRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HechoServicio implements IHechoServicio {

    @Autowired
    private IHechosRepositorio hechoRepositorio;

    @Override
    public List<Hecho> extraerHechos() {
        //TODO: Se extrae hecho del archivo y carga en el repositorio (Nico)
        return List.of();
    }

    @Override
    public List<Hecho> obtenerHechos() {
        //TODO: Obtiene los hechos del repositorio (Nico)
        //obtengo hechos del repositorio tranformarlos en DTO y mandarlo al controler
        return List.of();
    }
}
