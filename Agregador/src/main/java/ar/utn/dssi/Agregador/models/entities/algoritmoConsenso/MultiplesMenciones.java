package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultiplesMenciones implements IAlgoritmoDeConsenso{

    @Autowired
    private IColeccionRepository coleccionRepository;

    List<Coleccion> colecciones = coleccionRepository.findall();

    @Override
    public Boolean consensuar(List<Hecho> hechosDelAgregador, List<Hecho> hechosDeColeccion) {
        return  colecciones.stream().filter(coleccion -> coleccion.contieneHecho(hecho))
            .limit(2).count() >= 2
            && colecciones.stream().noneMatch(coleccion -> coleccion.contieneHechoParecido(hecho));
    }

}
