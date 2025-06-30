package ar.utn.dssi.Agregador.models.entities;

import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.IAlgoritmoDeConsenso;


import java.util.List;

public class CuradorHechos {
    List<IAlgoritmoDeConsenso> algoritmos;

    public CuradorHechos() {
        this.algoritmos = List.of(Consenso.values());
    }

}
