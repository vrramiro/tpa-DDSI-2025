package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.dto.input.ColeccionInputDTO;
import ar.utn.dssi.Agregador.dto.output.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.dto.output.HechoOutputDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public interface IColeccionService {
  ColeccionOutputDTO crearColeccion(ColeccionInputDTO coleccionInputDTO);

  Page<ColeccionOutputDTO> obtenerColecciones(Pageable pageable);

  ColeccionOutputDTO editarColeccion(String handle, ColeccionInputDTO coleccionInputDTO);

  void eliminarColeccion(String handle);

  Page<HechoOutputDTO> obtenerHechosDeColeccion(boolean navegacionCurada, String handle, LocalDate fechaReporteDesde, LocalDate fechaReporteHasta, LocalDate fechaAcontecimientoDesde, LocalDate fechaAcontecimientoHasta, String provincia, String ciudad, Pageable pageable);

  ColeccionOutputDTO obtenerColeccion(String handle);
}
