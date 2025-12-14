package ar.utn.dssi.app_web.services.Interfaces;

import ar.utn.dssi.app_web.dto.input.EstadisticaInputDTO;

public interface IEstadisticaService {
    EstadisticaInputDTO obtenerCantidadSpam();
    EstadisticaInputDTO obtenerCategoriaMasHechos();
    EstadisticaInputDTO obtenerProvinciaPorColeccion(Long idColeccion);
    EstadisticaInputDTO obtenerProvinciaPorCategoria(Long idCategoria);
    EstadisticaInputDTO obtenerHorasPorCategoria(Long idCategoria);
    byte[] exportarEstadisticas(String tipoArchivo);
}
