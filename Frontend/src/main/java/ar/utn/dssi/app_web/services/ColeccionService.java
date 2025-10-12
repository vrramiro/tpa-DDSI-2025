package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.ColeccionDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ColeccionService {

  public List<ColeccionDTO> obtenerTodasLasColecciones() {
    return null; //TODO Se conecta con el WebApiServices para obtener todas las colecciones
  }
}
