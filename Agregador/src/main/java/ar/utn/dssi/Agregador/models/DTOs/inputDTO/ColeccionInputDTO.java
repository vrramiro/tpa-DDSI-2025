package ar.utn.dssi.Agregador.models.DTOs.inputDTO;

import ar.utn.dssi.Agregador.models.entities.criterio.ICriterioDePertenencia;
import lombok.Data;

import java.util.List;

@Data
public class ColeccionInputDTO {
    private String titulo;
    private String descripcion;
    private List<ICriterioDePertenencia> criteriosDePertenecias;
    private String handle;
    }
