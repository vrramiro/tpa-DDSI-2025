package ar.utb.ba.dsi.Normalizador.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContenidoMultimedia {

    private Long id;

    private String url;
    private String formato;
    private Long tamano;

    private Hecho hecho;
}
