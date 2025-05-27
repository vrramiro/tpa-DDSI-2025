package ar.utn.dssi.FuenteEstatica.models.entities.importador;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;

public interface HechoFactory {
    public Hecho crearHecho (String lineaLeida);
}