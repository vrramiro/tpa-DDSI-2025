package ar.utn.dssi.Agregador.models.entities;

import java.util.List;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.IAlgoritmoDeConsenso;
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
    private List<Fuente> fuentesDeHechos;
    private IAlgoritmoDeConsenso algoritmoConsenso;
    private String handle;

    public Coleccion() {
        this.hechos = new java.util.ArrayList<>();
    }

    public void aplicarAlgoritmoConsenso() {
        List<Long> idsFuentes = this.getFuentesDeHechos().stream().map(Fuente::getIdFuente).toList();

        List<Hecho> hechosRecienConsensuados = this.algoritmoConsenso.consensuar(this.hechos, idsFuentes);
        hechosConsensuados = hechosRecienConsensuados;
    }
}