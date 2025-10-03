package ar.utn.dssi.FuenteEstatica.services;

import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.io.File;

public interface IHechoServicio {
    void importarArchivo(File archivo);
    List<HechoOutputDTO> obtenerHechos(LocalDateTime fechaDesde);
}
