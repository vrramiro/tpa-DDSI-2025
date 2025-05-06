package ar.utn.ba.dsi.MetaMap.modelos.repositorios.fuente;

import ar.edu.utn.frba.dds.contenido.Hecho;

import java.time.LocalDate;

public interface HechoFactory {
     public Hecho crearHecho (String lineaLeida);
}

