package ar.utn.dssi.Estadisticas.models.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudDeEliminacion {
    private Long id;
    private Boolean esSpam;
}
