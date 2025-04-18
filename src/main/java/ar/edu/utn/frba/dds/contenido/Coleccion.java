package ar.edu.utn.frba.dds.contenido;

import ar.edu.utn.frba.dds.criterio.CriterioDePertenecia;
import ar.edu.utn.frba.dds.fuente.Fuente;

import java.util.List;
import java.util.stream.Collectors;

public class Coleccion {
    private String titulo;
    private String descripcion;
    private List<Hecho> hechos;
    private Fuente fuenteDeOrigen;
    private List<CriterioDePertenecia> criteriosDePertenecias;

    public void cargarHechos() {
        this.hechos = fuenteDeOrigen
                .obtenerHechos()
                .stream()
                .filter(hecho -> criteriosDePertenecias
                        .stream()
                        .allMatch(criterio -> criterio.hechoLoCumple(hecho)))
                .collect(Collectors.toList());
    }

}
