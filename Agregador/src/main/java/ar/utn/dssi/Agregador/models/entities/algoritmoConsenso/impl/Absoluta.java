package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.impl;

import ar.utn.dssi.Agregador.models.entities.Consenso;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.IAlgoritmoDeConsenso;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Absoluta implements IAlgoritmoDeConsenso {
    private Consenso consenso = Consenso.ABSOLUTO;

    public static Hecho curar(Hecho hecho, List<Fuente> fuentesDelSistema) {
        
    }
}

