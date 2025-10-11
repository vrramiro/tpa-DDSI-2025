package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface IColeccionService {
    //CRUD
    ColeccionOutputDTO crearColeccion(ColeccionInputDTO coleccionInputDTO);
    List<ColeccionOutputDTO> obtenerColecciones();
    ColeccionOutputDTO editarColeccion(String handle, ColeccionInputDTO coleccionInputDTO);
    void eliminarColeccion(String handle);
    List<HechoOutputDTO> obtenerHechosDeColeccion(String modoNavegacion, String handle, LocalDate fechaReporteDesde, LocalDate fechaReporteHasta, LocalDate fechaAcontecimientoDesde, LocalDate fechaAcontecimientoHasta, String provincia, String ciudad);

}
