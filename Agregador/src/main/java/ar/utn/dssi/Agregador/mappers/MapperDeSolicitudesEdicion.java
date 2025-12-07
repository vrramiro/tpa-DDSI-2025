package ar.utn.dssi.Agregador.mappers;

import ar.utn.dssi.Agregador.dto.output.SolicitudEdicionOutputDTO;
import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEdicion;

public class MapperDeSolicitudesEdicion {
    public static SolicitudEdicionOutputDTO toDTO(SolicitudDeEdicion entidad) {
        if (entidad == null) return null;

        SolicitudEdicionOutputDTO dto = new SolicitudEdicionOutputDTO();
        dto.setId(entidad.getId());
        dto.setHechoOriginal(MapperDeHechos.hechoToOutputDTO(entidad.getHechoOriginal()));

        dto.setNuevoTitulo(entidad.getNuevoTitulo());
        dto.setNuevaDescripcion(entidad.getNuevaDescripcion());
        dto.setNuevoIdCategoria(entidad.getNuevoIdCategoria());
        dto.setEstado(entidad.getEstado());
        dto.setFechaCreacion(entidad.getFechaCreacion());
        dto.setFechaEvaluacion(entidad.getFechaEvaluacion());

        return dto;
    }
}