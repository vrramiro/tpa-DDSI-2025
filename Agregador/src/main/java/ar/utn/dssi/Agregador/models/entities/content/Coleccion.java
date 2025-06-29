package ar.utn.dssi.Agregador.models.entities.content;

import java.util.List;
import ar.utn.dssi.Agregador.models.entities.criterio.ICriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Coleccion {
    private List<Hecho> hechos;
    private String titulo;
    private String descripcion;
    private List<ICriterioDePertenencia> criteriosDePertenecias;
    //private List<Fuente> fuentesDeHechos; //TODO
    private String handle;      // identificador textual único que se utiliza para distinguirla dentro del sistema

    public Coleccion() {
        this.hechos = new java.util.ArrayList<>();
    }
}