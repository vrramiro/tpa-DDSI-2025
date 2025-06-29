package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
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
