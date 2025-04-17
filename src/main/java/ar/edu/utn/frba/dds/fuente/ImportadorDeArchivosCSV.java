package ar.edu.utn.frba.dds.fuente;

import ar.edu.utn.frba.dds.contenido.Hecho;
import ar.edu.utn.frba.dds.criterio.Categoria;
import ar.edu.utn.frba.dds.contenido.Ubicacion;
import ar.edu.utn.frba.dds.contenido.Origen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ImportadorDeArchivosCSV implements ImportadorDeArchivos{
    @Override
    public List<Hecho> importarHechos(File archivo) {
        List<Hecho> hechos = new ArrayList<>();

        //LEO ARCHIVO CSV
        try {
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            String linea;

            lector.readLine(); //Salta el encabezado
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");

                String titulo = campos[0].trim();
                String descripcion = campos[1].trim();
                Categoria categoria = new Categoria(campos[2].trim());

                double latitud = Double.parseDouble(campos[3].trim());
                double longitud = Double.parseDouble(campos[4].trim());

                // Fecha en formato dd/MM/yyyy
                String[] partesFecha = campos[5].trim().split("/");
                int dia = Integer.parseInt(partesFecha[0]);
                int mes = Integer.parseInt(partesFecha[1]);
                int anio = Integer.parseInt(partesFecha[2]);

                LocalDateTime fechaHecho = LocalDateTime.of(anio, mes, dia, 0, 0);
                LocalDateTime fechaCarga = LocalDateTime.now();

                Ubicacion ubicacion = new Ubicacion("Lugar no especificado", latitud, longitud);

                Hecho hecho = new Hecho(
                        titulo, descripcion, categoria, ubicacion, fechaHecho, fechaCarga, Origen.ARCHIVO, true
                );

                hechos.add(hecho);
            }

            lector.close();

        } catch (Exception e) {
            System.out.println("Error al importar hechos: " + e.getMessage());
        }

        return hechos;
    }
}
