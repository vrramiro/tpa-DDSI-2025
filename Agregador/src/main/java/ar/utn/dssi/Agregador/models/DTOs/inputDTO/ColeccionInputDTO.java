package ar.utn.dssi.Agregador.models.DTOs.inputDTO;

import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.TipoConsenso;
import lombok.Data;

import java.util.List;

@Data
public class ColeccionInputDTO {
    private String titulo;
    private String descripcion;
    private List<CriterioDePertenenciaInputDTO> criteriosDePertenecias;
    private List<FuenteInputDTO> fuentes;
    private TipoConsenso consenso;
}
