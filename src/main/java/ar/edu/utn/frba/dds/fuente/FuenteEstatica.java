package ar.edu.utn.frba.dds.fuente;

import ar.edu.utn.frba.dds.contenido.Hecho;

import java.io.File;
import java.util.List;

public class FuenteEstatica {
    private ImportadorDeArchivos importadorDeArchivos;

    public FuenteEstatica(ImportadorDeArchivos importadorDeArchivos) {
        this.importadorDeArchivos = importadorDeArchivos;
    }

    public List<Hecho> obtenerHechos(File archivo) {
        return importadorDeArchivos.importarHechos(archivo);
    }
}
