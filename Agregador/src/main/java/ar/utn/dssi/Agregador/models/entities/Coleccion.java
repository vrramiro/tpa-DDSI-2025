package ar.utn.dssi.Agregador.models.entities;

import java.util.List;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.IAlgoritmoDeConsenso;
import ar.utn.dssi.Agregador.models.entities.criterio.ICriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.ICriterioDeFiltrado;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Coleccion {
    private List<Hecho> hechos;
    private String titulo;
    private String descripcion;
    private List<ICriterioDeFiltrado> criteriosDePertenecias;
    private List<Fuente> fuentesDeHechos;
    private IAlgoritmoDeConsenso algoritmoConsenso;
    private String handle;

    public Coleccion() {
        this.hechos = new java.util.ArrayList<>();
    }

    public Boolean contieneHecho(Hecho hechoBuscado) {
        return this.hechos.stream().anyMatch(hecho -> hecho.mismoHecho(hechoBuscado));
    }

    public Boolean contieneHechoParecido(Hecho hechoBuscado) {
        return this.hechos.stream().anyMatch(hecho -> hecho.mismoMismoTitulo(hechoBuscado) && hecho.distintosAtributos(hechoBuscado));
    }

    public void aplicarAlgoritmoConsenso(List<Hecho> hechosDelAgregador) {
       this.algoritmoConsenso.consensuar(hechosDelAgregador, this.hechos);
    }
}