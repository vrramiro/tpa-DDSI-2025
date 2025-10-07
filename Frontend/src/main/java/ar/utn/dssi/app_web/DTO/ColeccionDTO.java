package ar.utn.dssi.app_web.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ColeccionDTO {
    private String titulo;
    private String descripcion;
    private ConsensoDTO consenso;
    List<CriterioDTO> criterios;
    List<FuenteDTO> fuentes;
}
