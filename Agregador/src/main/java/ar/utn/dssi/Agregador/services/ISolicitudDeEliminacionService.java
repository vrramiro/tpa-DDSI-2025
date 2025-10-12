package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.dto.input.SolicitudDeEliminacionInputDTO;
import ar.utn.dssi.Agregador.dto.input.SolicitudProcesadaInputDTO;
import ar.utn.dssi.Agregador.dto.output.SolicitudDeEliminacionOutputDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ISolicitudDeEliminacionService {
  SolicitudDeEliminacionOutputDTO crearSolicitudDeEliminacion(SolicitudDeEliminacionInputDTO solicitudDeEliminacion);

  void procesarSolicitud(Long idSolicitud, SolicitudProcesadaInputDTO solicitudProcesada);

  List<SolicitudDeEliminacionOutputDTO> obtenerSolicitudes(String tipoEstado, Boolean spam);
}
