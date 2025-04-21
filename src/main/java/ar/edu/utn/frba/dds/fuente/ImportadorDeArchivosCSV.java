package ar.edu.utn.frba.dds.fuente;

import ar.edu.utn.frba.dds.contenido.Etiqueta;
import ar.edu.utn.frba.dds.contenido.Hecho;
import ar.edu.utn.frba.dds.criterio.Categoria;
import ar.edu.utn.frba.dds.contenido.Ubicacion;
import ar.edu.utn.frba.dds.contenido.Origen;
import ar.edu.utn.frba.dds.contenido.HechosEliminados;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;


public class ImportadorDeArchivosCSV implements ImportadorDeArchivos{
    @Override
    public List<Hecho> importarHechos(File archivo) {
        List<Hecho> hechos = new java.util.ArrayList<>();
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

                String titulo = campos[0].trim();
                String descripcion = campos[1].replaceAll("^\"|\"$", "").trim();
                String nombreCategoria = campos[2].trim();
                double latitud = Double.parseDouble(campos[3].trim());
                double longitud = Double.parseDouble(campos[4].trim());
                LocalDate fecha = LocalDate.parse(campos[5].trim(), formatter);

                Categoria categoria = new Categoria(nombreCategoria);
                Ubicacion ubicacion = new Ubicacion(latitud, longitud);
                LocalDateTime fechaAcontecimiento = fecha.atStartOfDay();
                LocalDateTime fechaCarga = LocalDateTime.now();
                Origen origen = Origen.ARCHIVO;
                List<Etiqueta> etiquetas = Collections.emptyList(); // si después las cargás por otro lado
                boolean visible = true;

                Hecho hecho = new Hecho(titulo, descripcion, categoria, ubicacion,
                    fechaAcontecimiento, fechaCarga, origen, etiquetas, visible);

                if (!HechosEliminados.getHechosEliminados().contains(hecho)) {
                    hechos.add(hecho);
                }
            }

        } catch (Exception e) {
            System.err.println("Error al importar hechos: " + e.getMessage());
            e.printStackTrace();
        }
        return hechos;
    }
}
