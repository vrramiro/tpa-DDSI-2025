package ar.utn.dssi.Agregador.modelos.DTOs.outputDTO;

import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;
import ar.utn.dssi.Agregador.modelos.entidades.criterio.CriterioDePertenencia;
import lombok.Data;

import java.util.List;

@Data
public class ColeccionOutputDTO {
    private List<Hecho> hechos;
    private String titulo;
    private String descripcion;
}