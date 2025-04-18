package ar.edu.utn.frba.dds.contenido;

import ar.edu.utn.frba.dds.contenido.Hecho;
import lombok.Getter;

import java.util.List;

public class HechosEliminados {
    @Getter
    private static List<Hecho> hechosEliminados;

    public static void agregarHecho(Hecho hechoEliminado) {
        hechosEliminados.add(hechoEliminado);
    }

}
