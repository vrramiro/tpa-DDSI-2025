package ar.utn.dssi.FuenteDinamica.models.mappers;

import ar.utn.dssi.FuenteDinamica.models.entities.ContenidoMultimedia;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class MapperContenidoMultimedia {
    public static List<ContenidoMultimedia> convertirMultipartAContenido(List<MultipartFile> files, Hecho hecho) {
        List<ContenidoMultimedia> listaContenido = new ArrayList<>();

        for (MultipartFile file : files) {
            ContenidoMultimedia cm = new ContenidoMultimedia();
            cm.setUrl("/uploads/" + file.getOriginalFilename()); // TODO: Generar la URL real
            cm.setFormato(file.getContentType());
            cm.setTamano(file.getSize());
            cm.setHecho(hecho);

            listaContenido.add(cm);
        }

        return listaContenido;
    }
}
