package ar.utb.ba.dsi.estadisticas.models.adapters.agregador;

import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.ColeccionInputDTO;
import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.HechoInputDTO;
import java.util.List;

public interface IAgregadorAdapter {
    public List<HechoInputDTO> obtenerHechos();
    public List<ColeccionInputDTO> obtenerColecciones();
}
