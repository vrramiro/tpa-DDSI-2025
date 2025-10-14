package ar.utn.dssi.FuenteEstatica.services;

import ar.utn.dssi.FuenteEstatica.dto.output.HechoOutputDTO;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public interface IHechoServicio {
  void importarArchivo(File archivo);

  List<HechoOutputDTO> obtenerHechos(LocalDateTime fechaDesde);
}
