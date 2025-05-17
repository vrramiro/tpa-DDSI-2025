package ar.utn.dssi.FuenteEstatica.services;

import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;

import java.util.List;
import java.io.File;

public interface IHechoServicio {
    List<HechoOutputDTO> extraerHechos(File archivo);
    List<HechoOutputDTO> obtenerHechos();
}
