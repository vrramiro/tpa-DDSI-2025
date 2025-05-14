package ar.utb.ba.dsi.modelos.entidades.contenido;

import ar.utn.ba.dsi.MetaMap.metaMap.modelos.entidades.criterio.CriterioDePertenecia;

import ar.edu.utn.frba.dds.fuente.FuenteEstatica;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class Coleccion {
    private String titulo;
    private String descripcion;
    private List<Hecho> hechos;
    private FuenteEstatica fuenteDeOrigen;
    private List<CriterioDePertenecia> criteriosDePertenecias;

    public Coleccion(String titulo, String descripcion, FuenteEstatica fuenteDeOrigen, List<CriterioDePertenecia> criteriosDePertenecias) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fuenteDeOrigen = fuenteDeOrigen;
        this.criteriosDePertenecias = criteriosDePertenecias;
        this.hechos = new ArrayList<>();
    }

    public void cargarHechos() {
        this.hechos = fuenteDeOrigen
                .obtenerHechos()
                .stream()
                .filter(hecho -> criteriosDePertenecias
                        .stream()
                        .allMatch(criterio -> criterio.hechoLoCumple(hecho)))
                .collect(Collectors.toList());
    }

    public void addCriterioDePertenencia(CriterioDePertenecia nuevoCriterio) {
        this.criteriosDePertenecias.add(nuevoCriterio);
    }
}
