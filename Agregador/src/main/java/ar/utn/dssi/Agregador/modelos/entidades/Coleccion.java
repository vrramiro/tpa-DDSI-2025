package ar.utn.dssi.Agregador.modelos.entidades;

import ar.utn.dssi.Agregador.modelos.entidades.criterio.CriterioDePertenencia;

import java.util.List;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Coleccion {
    private long idColeccion;
    private String titulo;
    private String descripcion;
    private List<CriterioDePertenencia> criteriosDePertenecias;
    private String handle;

    public void addCriterioDePertenencia(CriterioDePertenencia nuevoCriterio) {
        this.criteriosDePertenecias.add(nuevoCriterio);
    }
}