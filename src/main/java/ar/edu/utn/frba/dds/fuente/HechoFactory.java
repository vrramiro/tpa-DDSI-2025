package ar.edu.utn.frba.dds.fuente;

import ar.edu.utn.frba.dds.contenido.Hecho;

import java.time.LocalDate;

public interface HechoFactory {
     public Hecho crearHecho(
            String titulo,
            String descripcion,
            String categoria,
            double latitud,
            double longitud,
            LocalDate fechaAcontecimiento
    );
}

