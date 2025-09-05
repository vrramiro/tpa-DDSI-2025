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
    private List<Hecho> hechos;
    private String descripcion;
    private List<ICriterioDeFiltrado> criterios;
    private List<Fuente> fuentesDeHechos;
    private AlgoritmoConsenso algoritmoConsenso;
    private String handle;
    private Boolean actualizada;

    public Coleccion() {
        this.hechos = new java.util.ArrayList<>();
    }

    //TODO no va aca
    public void aplicarAlgoritmoConsenso(List<Hecho> hechosAConsensuar,List<Fuente> fuentesDelSistema) {
        List<Hecho> hechosRecienConsensuados = this.algoritmoConsenso.consensuar(hechosAConsensuar, fuentesDelSistema);
        hechos = hechosRecienConsensuados;
    }

    public void agregarHechos(List<Hecho> nuevosHechos) {
        this.hechos.addAll(nuevosHechos.stream().filter(this::lePertenece).toList());
    }

    private Boolean lePertenece(Hecho hecho) {
        return this.criterios.stream().allMatch(criterio -> criterio.loCumple(hecho));
    }
}