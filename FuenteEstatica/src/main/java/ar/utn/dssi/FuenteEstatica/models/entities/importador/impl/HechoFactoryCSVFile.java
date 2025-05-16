package ar.utn.dssi.FuenteEstatica.models.entities.importador.impl;


import ar.utn.dssi.FuenteEstatica.models.entities.contenido.*;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.HechoFactory;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class HechoFactoryCSVFile implements HechoFactory {
    public Hecho crearHecho(String lineaLeida) {
        String[] campos = lineaLeida.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (campos.length < 6) {
            System.err.println("Línea inválida (faltan campos): " + lineaLeida);
            return null;
        }

        String tituloLeida = campos[0].trim();
        String descripcionLeida = campos[1].replaceAll("^\"|\"$", "").trim();
        String categoriaLeida = campos[2].trim();
        double latitudLeida = Double.parseDouble(campos[3].trim());
        double longitudLeida = Double.parseDouble(campos[4].trim());
        LocalDate fechaLeida = LocalDate.parse(campos[5].trim(), formatter);

        return Hecho.builder()
                .titulo(tituloLeida)
                .descripcion(descripcionLeida)
                .categoria(new Categoria(categoriaLeida))
                .ubicacion(new Ubicacion(latitudLeida, longitudLeida))
                .fechaAcontecimiento(fechaLeida.atStartOfDay())
                .fechaCarga(LocalDateTime.now())
                .build();
    }
}
