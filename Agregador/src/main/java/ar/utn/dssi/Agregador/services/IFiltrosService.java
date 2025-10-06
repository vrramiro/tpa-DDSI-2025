package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.entities.FiltroCriterioPertenecia;
import org.springframework.stereotype.Service;

@Service
public interface IFiltrosService {
    FiltroCriterioPertenecia crearFiltro(FiltroInputDTO filtro);
}
