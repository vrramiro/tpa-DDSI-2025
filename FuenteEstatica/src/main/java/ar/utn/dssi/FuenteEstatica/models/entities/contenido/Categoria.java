package ar.utn.dssi.FuenteEstatica.models.entities.contenido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Categoria {
    private String nombre;
    private Long idCategoria;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }
}