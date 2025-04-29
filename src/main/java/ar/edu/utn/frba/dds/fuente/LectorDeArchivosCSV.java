package ar.edu.utn.frba.dds.fuente;

import ar.edu.utn.frba.dds.contenido.*;
import ar.edu.utn.frba.dds.criterio.Categoria;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LectorDeArchivosCSV implements ImportadorDeArchivos {

    private HechoFactory hechoFactory;

    @Override
    public List<Hecho> importarHechos(File archivo) {
        List<Hecho> hechos = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8))) {
            String linea;
            boolean esPrimeraLinea = true;


            while ((linea = reader.readLine()) != null) {
                if (esPrimeraLinea) {
                    esPrimeraLinea = false; // saltar encabezado
                    continue;
                }

                String[] campos = linea.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (campos.length < 6) {
                    System.err.println("Línea inválida (faltan campos): " + linea);
                    continue;
                }

                String tituloLeida = campos[0].trim();
                String descripcionLeida = campos[1].replaceAll("^\"|\"$", "").trim();
                String categoriaLeida = campos[2].trim();
                double latitudLeida = Double.parseDouble(campos[3].trim());
                double longitudLeida = Double.parseDouble(campos[4].trim());
                LocalDate fechaLeida = LocalDate.parse(campos[5].trim(), formatter);

                Hecho hecho = hechoFactory.crearHecho(tituloLeida, descripcionLeida, categoriaLeida, latitudLeida, longitudLeida, fechaLeida);

                if (!HechosEliminados.getHechosEliminados().contains(hecho)) {
                    hechos.add(hecho);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return hechos;
    }
}