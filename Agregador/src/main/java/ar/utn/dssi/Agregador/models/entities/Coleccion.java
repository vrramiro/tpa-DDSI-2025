package ar.utn.dssi.Agregador.models.entities;

import java.util.List;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.AlgoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.ICriterioDeFiltrado;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Coleccion {
    private List<Hecho> hechos;
    private String titulo;
    private List<Hecho> hechosConsensuados;
    private String descripcion;
    private List<ICriterioDeFiltrado> criteriosDePertenecias;
    //private List<Fuente> fuentesDeHechos;
    private AlgoritmoConsenso algoritmoConsenso;
    private String handle;
    private Boolean actualizada;

    public Coleccion() {
        this.hechos = new java.util.ArrayList<>();
    }

    public void aplicarAlgoritmoConsenso(List<Hecho> hechosAConsensuar,List<Fuente> fuentesDelSistema) {
        List<Hecho> hechosRecienConsensuados = this.algoritmoConsenso.consensuar(hechosAConsensuar, fuentesDelSistema);
        hechosConsensuados = hechosRecienConsensuados;
    }

    public boolean cumpleCriterios(Hecho hecho) {
        return criteriosDePertenecias.stream().allMatch(criterio -> criterio.loCumple(hecho));
    }
}