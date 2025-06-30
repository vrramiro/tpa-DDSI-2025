package ar.utn.dssi.Agregador.models.entities.modoNavegacion;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FiltroInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.Filtro;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.IAlgoritmoDeConsenso;

import java.util.List;

public interface IModoNavegacion {
    Boolean hechoNavegable(Hecho hecho, IAlgoritmoDeConsenso algoritmoDeConsenso);
}
