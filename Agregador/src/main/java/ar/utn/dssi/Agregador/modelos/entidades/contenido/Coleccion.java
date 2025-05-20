package ar.utn.dssi.Agregador.modelos.entidades.contenido;

import ar.utn.dssi.Agregador.modelos.entidades.criterio.CriterioDePertenencia;

import java.util.List;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Coleccion {
    private String idColeccion; //para que sea alfanumerico
    private String titulo;
    private String descripcion;
    private List<CriterioDePertenencia> criteriosDePertenecias;

    public Coleccion(String titulo, String descripcion, List<CriterioDePertenencia> criteriosDePertenecias) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.criteriosDePertenecias = criteriosDePertenecias;
    }

    public void addCriterioDePertenencia(CriterioDePertenencia nuevoCriterio) {
        this.criteriosDePertenecias.add(nuevoCriterio);
    }
}