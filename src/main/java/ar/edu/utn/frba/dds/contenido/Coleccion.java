package ar.edu.utn.frba.dds.contenido;

import ar.edu.utn.frba.dds.criterio.CriterioDePertenecia;

import ar.edu.utn.frba.dds.fuente.FuenteEstatica;
import lombok.Getter;
import lombok.Setter;

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


    public List<Hecho> aplicarFiltros(Etiqueta ... etiquetas) {
        return this.hechos
            .stream()
            .filter(hecho ->
                List.of(etiquetas)
                    .stream().allMatch(etiqueta -> hecho.tieneEtiqueta(etiqueta))
            )
            .collect(Collectors.toList());
    }


    public void addCriterioDePertenencia(CriterioDePertenecia nuevoCriterio) {
        this.criteriosDePertenecias.add(nuevoCriterio);
    }
}
