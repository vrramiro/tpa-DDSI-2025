package ar.utn.dssi.FuenteEstatica.models.repositories.implementation;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import ar.utn.dssi.FuenteEstatica.models.repositories.IHechosRepositorio;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HechoRepositorio implements IHechosRepositorio {
    private List<Hecho> hechos = new ArrayList<>();

    @Override
    public void save(List<Hecho> hechosImportados) {
        if (hechosImportados.size() >= 10000){ //TODO el if deberia ser un try catch en el servise
            hechos.addAll(hechosImportados);
        }
    }

    @Override
    public List<Hecho> findAll() {
        return this.hechos;
    }

}
