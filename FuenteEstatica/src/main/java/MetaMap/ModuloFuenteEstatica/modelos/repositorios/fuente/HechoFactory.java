package ar.utb.ba.dsi.modelos.repositorios.fuente;

import ar.edu.utn.frba.dds.contenido.Hecho;

import java.time.LocalDate;

public interface HechoFactory {
     public Hecho crearHecho (String lineaLeida);
}

