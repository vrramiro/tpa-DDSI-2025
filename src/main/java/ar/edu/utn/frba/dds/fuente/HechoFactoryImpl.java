package ar.edu.utn.frba.dds.fuente;

import ar.edu.utn.frba.dds.contenido.Hecho;
import ar.edu.utn.frba.dds.criterio.Categoria;
import ar.edu.utn.frba.dds.contenido.Ubicacion;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HechoFactoryImpl implements HechoFactory {

    public Hecho crearHecho(String titulo, String descripcion, String categoria, double latitud, double longitud, LocalDate fechaAcontecimiento) {
        return Hecho.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .categoria(new Categoria(categoria))
                .ubicacion(new Ubicacion(latitud, longitud))
                .fechaAcontecimiento(fechaAcontecimiento.atStartOfDay())
                .fechaCarga(LocalDateTime.now())
                .build();
    }
}
