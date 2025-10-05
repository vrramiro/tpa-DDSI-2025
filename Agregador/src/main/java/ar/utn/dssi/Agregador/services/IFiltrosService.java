package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FiltroInputDTO;
import ar.utn.dssi.Agregador.models.entities.Filtro;
import org.springframework.stereotype.Service;

@Service
public interface IFiltrosService {
    Filtro crearFiltro(FiltroInputDTO filtro);
}
