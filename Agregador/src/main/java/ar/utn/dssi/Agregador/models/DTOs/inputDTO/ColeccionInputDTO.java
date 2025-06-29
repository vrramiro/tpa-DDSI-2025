package ar.utn.dssi.Agregador.models.DTOs.inputDTO;

import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.ICriterioDeFiltrado;
import lombok.Data;

import java.util.List;

@Data
public class ColeccionInputDTO {
    private String titulo;
    private String descripcion;
    private List<ICriterioDeFiltrado> criteriosDePertenecias;
    private String handle;
}
