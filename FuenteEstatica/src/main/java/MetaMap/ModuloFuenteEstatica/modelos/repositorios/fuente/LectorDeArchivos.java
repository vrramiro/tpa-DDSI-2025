package ar.utb.ba.dsi.modelos.repositorios.fuente;

import ar.edu.utn.frba.dds.contenido.Hecho;

import java.io.File;
import java.util.List;

public interface LectorDeArchivos {
    public List<Hecho> importarHechos(File archivo);
}
