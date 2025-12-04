package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.dto.input.ColeccionInputDTO;
import ar.utn.dssi.Agregador.dto.output.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.dto.output.HechoOutputDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public interface IColeccionService {
  ColeccionOutputDTO crearColeccion(ColeccionInputDTO coleccionInputDTO);

  Page<ColeccionOutputDTO> obtenerColecciones(Pageable pageable);

  ColeccionOutputDTO editarColeccion(String handle, ColeccionInputDTO coleccionInputDTO);

  void eliminarColeccion(String handle);

  Page<HechoOutputDTO> obtenerHechosDeColeccion(String modoNavegacion, String handle, LocalDate fechaReporteDesde, LocalDate fechaReporteHasta, LocalDate fechaAcontecimientoDesde, LocalDate fechaAcontecimientoHasta, String provincia, String ciudad, Pageable pageable);

  ColeccionOutputDTO obtenerColeccion(String handle);
}
