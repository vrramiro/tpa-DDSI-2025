package ar.utn.dssi.Agregador.models.DTOs.outputDTO;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import lombok.Data;

import java.util.List;

@Data
public class ColeccionOutputDTO {
    private List<Hecho> hechos;
    private String titulo;
    private String descripcion;
}