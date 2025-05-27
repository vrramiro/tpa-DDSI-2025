package ar.utn.dssi.FuenteEstatica.models.entities.importador.impl;

import ar.utn.dssi.FuenteEstatica.models.entities.importador.ILectorDeArchivos;

import java.io.File;

public class FactoryLector {

    public static ILectorDeArchivos crearLector(File archivo) {
        ILectorDeArchivos instancia = null;
        String nombreArchivo = archivo.getName();
        String extension = obtenerExtension(nombreArchivo);

        switch (extension) {
            case "csv":
                instancia = new LectorDeArchivosCSV();
                break;
            default:
                throw new RuntimeException("No se ha podido crear el lector de archivo con extensión: " + extension);
        }

        return instancia;
    }

    private static String obtenerExtension(String nombreArchivo) {
        int punto = nombreArchivo.lastIndexOf('.');
        if (punto != -1 && punto < nombreArchivo.length() - 1) {
            return nombreArchivo.substring(punto + 1).toLowerCase();
        }
        return "";
    }
}