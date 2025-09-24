package ar.utn.dssi.FuenteEstatica.models.entities.importador.impl;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.HechoFactory;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.ILectorDeArchivos;
import ar.utn.dssi.FuenteEstatica.models.repositories.IHechosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class LectorDeArchivosCSV implements ILectorDeArchivos {
    private final HechoFactory hechoFactory;

    public LectorDeArchivosCSV(HechoFactory hechoFactory) {
        this.hechoFactory = hechoFactory;
    }
    @Override
    public List<Hecho> importarHechos(File archivo) {
        List<Hecho> hechos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8))) {
            String linea;
            boolean esPrimeraLinea = true;

            while ((linea = reader.readLine()) != null) {
                if (esPrimeraLinea) {
                    esPrimeraLinea = false; // saltar encabezado
                    continue;
                }

                Hecho hechoObtenido = hechoFactory.crearHecho(linea);

                if (hechoObtenido == null){
                    throw new RuntimeException("No se pudo obtener el hecho, faltan campos.");
                }
                hechos.add(hechoObtenido);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return hechos;
    }
}