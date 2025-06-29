package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MayoriaSimple implements IAlgoritmoDeConsenso{

    @Autowired
    private  IColeccionRepository coleccionRepository;

    List<Coleccion> colecciones = coleccionRepository.findall();

    private long cantidadDeFuentesEnColeccion = colecciones;

    @Override
    public Boolean consensuar(List<Hecho> hechosDelAgregador, List<Hecho> hechosDeColeccion) {
        long contienenHecho = colecciones.stream().filter(coleccione -> coleccione.contieneHecho(hecho)).count();

        return contienenHecho >= (cantidadDeFuentesEnColeccion/2);
    }
}

