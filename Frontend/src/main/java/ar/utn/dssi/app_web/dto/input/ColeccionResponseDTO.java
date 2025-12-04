package ar.utn.dssi.app_web.dto.input;

import ar.utn.dssi.app_web.dto.Criterio.CriterioDTO;
import ar.utn.dssi.app_web.dto.Fuente.TipoFuente;
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
    private String handle;
    private String titulo;
    private String descripcion;
    private String consenso;
    private List<CriterioDTO> criterios;
    private List<TipoFuente> fuentes;
}
