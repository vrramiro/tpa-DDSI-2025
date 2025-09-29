package ar.utn.dssi.FuenteDinamica.models.mappers;

import ar.utn.dssi.FuenteDinamica.models.entities.ContenidoMultimedia;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class MapperContenidoMultimedia {

    public static ContenidoMultimedia convertirAContenido(String urlArchivo, Hecho hecho) {
        ContenidoMultimedia cm = new ContenidoMultimedia();
        cm.setUrl(urlArchivo);
        cm.setHecho(hecho);
        return cm;
    }
}
