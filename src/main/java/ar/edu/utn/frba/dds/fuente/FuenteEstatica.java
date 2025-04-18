package ar.edu.utn.frba.dds.fuente;

import ar.edu.utn.frba.dds.contenido.Hecho;

import java.io.File;
import java.util.List;

public class FuenteEstatica implements Fuente {
    private ImportadorDeArchivos importadorDeArchivos;
    private File archivoDeHechos;

    public FuenteEstatica(ImportadorDeArchivos importadorDeArchivos, File archivoDeHechos) {
        this.importadorDeArchivos = importadorDeArchivos;
        this.archivoDeHechos = archivoDeHechos;
    }

    public List<Hecho> obtenerHechos() {
        return importadorDeArchivos.importarHechos(this.archivoDeHechos);
    }
}
