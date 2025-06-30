package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public abstract class AlgoritmoConsenso {
    private List<Hecho> hechosConsensuados;

    public AlgoritmoConsenso() {
        this.hechosConsensuados = new ArrayList<>();
    }

    public List<Hecho> consensuar(List<Hecho> hechos,List<Fuente> fuentes){
        hechos.forEach(hecho -> {
            if(cumpleAlgoritmo(hecho,fuentes)){
                hechosConsensuados.add(hecho);
            }
        });

        return hechosConsensuados;
    }

    public abstract Boolean cumpleAlgoritmo(Hecho hecho, List<Fuente> fuentes);
}
