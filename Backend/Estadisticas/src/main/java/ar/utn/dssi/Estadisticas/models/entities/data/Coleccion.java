package ar.utn.dssi.Estadisticas.models.entities.data;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class Coleccion {
    private Long id;
    private String nombre;
    private List<Hecho> hechos;
}
