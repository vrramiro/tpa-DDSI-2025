package ar.utn.dssi.app_web.dto.input;


import ar.utn.dssi.app_web.DTO.Consenso.ConsensoDTO;
import ar.utn.dssi.app_web.DTO.Criterio.CriterioDTO;
import ar.utn.dssi.app_web.DTO.Fuente.TipoFuente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ColeccionResponseDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private ConsensoDTO consenso;
    private CategoriaInputDTO categoria;
    private List<CriterioDTO> criterios;
    private List<TipoFuente> fuentes;
    private List<HechoInputDTO> hechos;
}
