package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.dto.output.HechoOutputDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IHechosService {
  List<HechoOutputDTO> obtenerHechos(LocalDate fechaReporteDesde,
                                     LocalDate fechaReporteHasta,
                                     LocalDate fechaAcontecimientoDesde,
                                     LocalDate fechaAcontecimientoHasta,
                                     Long idCategoria,
                                     String provincia);

  HechoOutputDTO obtenerHechoPorId(Long idHecho);

  void eliminarHecho(Long idHecho);

  void importarNuevosHechos();

  List<HechoOutputDTO> obtenerHechosPorAutor(String autor);
}
