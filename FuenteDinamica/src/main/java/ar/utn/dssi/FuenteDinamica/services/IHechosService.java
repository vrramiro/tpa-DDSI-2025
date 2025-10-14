package ar.utn.dssi.FuenteDinamica.services;

import ar.utn.dssi.FuenteDinamica.dto.input.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.dto.output.HechoOutputDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface IHechosService {
  List<HechoOutputDTO> obtenerHechos(LocalDateTime fechaDesde);

  void crear(HechoInputDTO hecho);

  void editarHecho(HechoInputDTO hecho, Long idHecho);

  void actualizarVisibilidad(Long idHecho, Boolean visibilidad);
}
