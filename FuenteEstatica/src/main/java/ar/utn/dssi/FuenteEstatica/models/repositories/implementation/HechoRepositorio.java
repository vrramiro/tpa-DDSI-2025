package ar.utn.dssi.FuenteEstatica.models.repositories.implementation;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import ar.utn.dssi.FuenteEstatica.models.repositories.IHechosRepositorio;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HechoRepositorio implements IHechosRepositorio {

    @Override
    public void save() {
        //TODO: Guardar los hechos en el repositorio (Santi)
    }

    @Override
    public List<Hecho> findAll() {
        //TODO: Trae todos los hechos del repositorio (Santi)
        return List.of();
    }

}
