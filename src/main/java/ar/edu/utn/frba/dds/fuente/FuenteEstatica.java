package ar.edu.utn.frba.dds.fuente;

import ar.edu.utn.frba.dds.contenido.Hecho;
import ar.edu.utn.frba.dds.contenido.HechosEliminados;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class FuenteEstatica {
    private ImportadorDeArchivos importadorDeArchivos;
    private File archivoDeHechos;


    public FuenteEstatica(ImportadorDeArchivos importadorDeArchivos, File archivoDeHechos) {
        this.importadorDeArchivos = importadorDeArchivos;
        this.archivoDeHechos = archivoDeHechos;
    }

    public List<Hecho> obtenerHechos() {
        // Solo devuelve hechos que NO esten eliminados
        return importadorDeArchivos
            .importarHechos(this.archivoDeHechos)
            .stream()
            .filter(hecho -> HechosEliminados.noContiene(hecho))
            .collect(Collectors.toList());
    }
}
