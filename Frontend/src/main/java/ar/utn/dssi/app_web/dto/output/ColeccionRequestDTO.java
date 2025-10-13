package ar.utn.dssi.app_web.dto.output;

import ar.utn.dssi.app_web.dto.CategoriaDTO;
import ar.utn.dssi.app_web.dto.Consenso.ConsensoDTO;
import ar.utn.dssi.app_web.dto.Criterio.CriterioDTO;
import ar.utn.dssi.app_web.dto.Fuente.TipoFuente;
import lombok.Data;

import java.util.List;

@Data
public class ColeccionRequestDTO {
    private String titulo;
    private String descripcion;
    private ConsensoDTO consenso;
    private CategoriaDTO categoria;
    private List<CriterioDTO> criterios;
    private List<TipoFuente> fuentes;
}
