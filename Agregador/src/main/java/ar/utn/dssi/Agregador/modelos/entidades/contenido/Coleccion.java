package ar.utn.dssi.Agregador.modelos.entidades.contenido;

import ar.utn.dssi.Agregador.modelos.entidades.criterio.CriterioDePertenencia;

import java.util.List;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Coleccion {
    private List<Hecho> hechos;
    private String titulo;
    private String descripcion;
    private List<CriterioDePertenencia> criteriosDePertenecias;
    private String handle;      // identificador textual único que se utiliza para distinguirla dentro del sistema

    public Coleccion() {
        this.hechos = new java.util.ArrayList<>();
    }

    public void addCriterioDePertenencia(CriterioDePertenencia nuevoCriterio) {
        this.criteriosDePertenecias.add(nuevoCriterio);
    }

}