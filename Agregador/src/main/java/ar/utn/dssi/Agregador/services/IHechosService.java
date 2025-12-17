package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.dto.output.HechoOutputDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IHechosService {
  List<HechoOutputDTO> obtenerHechos(LocalDate fechaReporteDesde,
                                     LocalDate fechaReporteHasta,
                                     LocalDate fechaAcontecimientoDesde,
                                     LocalDate fechaAcontecimientoHasta,
                                     Long idCategoria,
                                     String provincia,
                                     Double latMin, Double latMax, Double lonMin, Double lonMax); // Nuevos

  HechoOutputDTO obtenerHechoPorId(Long idHecho);

  void eliminarHecho(Long idHecho);

  void importarNuevosHechos();

  List<HechoOutputDTO> obtenerHechosPorAutor(String autor);

  Page<HechoOutputDTO> obtenerTodos(Pageable pageable);

  List<HechoOutputDTO> obtenerHechosRecientes(int limit);
}