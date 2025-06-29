package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.content.Coleccion;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;

import java.util.List;

public interface IAlgoritmoDeConsenso {
    //TODO arreglar
    List<Coleccion> colecciones = IColeccionRepository.findall();

    //por default estan consensuados
    public default Boolean consensuado(Hecho hecho){
        return Boolean.TRUE;
    }

}
