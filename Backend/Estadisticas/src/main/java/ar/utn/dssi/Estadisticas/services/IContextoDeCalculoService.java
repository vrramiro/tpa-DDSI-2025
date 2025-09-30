package ar.utn.dssi.Estadisticas.services;

import ar.utn.dssi.Estadisticas.models.entities.data.ContextoDeCalculo;
import org.springframework.stereotype.Service;


public interface IContextoDeCalculoService {
    ContextoDeCalculo obtenerContextoDeCalculo();
}
