package ar.utn.ba.dsi.MetaMap.prueba.modelos.repositorios.fuente;

import ar.utn.ba.dsi.MetaMap.prueba.modelos.entidades.contenido.Hecho;

public interface HechoFactory {
     public Hecho crearHecho (String lineaLeida);
}

