package ar.utb.ba.dsi.estadisticas.models.DTOs.inputs;

import lombok.Getter;

@Getter
public class SolicitudDeEliminacionInputDTO {
    //TODO Falta el endpoint en agregador directamente para que me de las solicitudes que son spam
    private Long id;
    // traigo algun dato solo para poder contar la cantidad, en el agregador deberia haber un endpoint que permita obtener las que eran spam unicamente
    //No hay info que me interese de las solicitudes salvo algo que me permita contarlas
    private Boolean esSpam;
}
