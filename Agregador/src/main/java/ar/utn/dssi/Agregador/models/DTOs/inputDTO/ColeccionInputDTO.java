package ar.utn.dssi.Agregador.modelos.DTOs.inputDTO;

import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;
import ar.utn.dssi.Agregador.modelos.entidades.criterio.CriterioDePertenencia;
import lombok.Data;

import java.util.List;

@Data
public class ColeccionInputDTO {
    private String titulo;
    private String descripcion;
    private List<CriterioDePertenencia> criteriosDePertenecias;
    private String handle;
    }
