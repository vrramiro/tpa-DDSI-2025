package ar.utn.dssi.Agregador.models.entities.content;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class Hecho {
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Ubicacion ubicacion;
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
    private List<MultipartFile> contenidoMultimedia;
    private Long idOrigen;
    private Long IdHecho;
    private Origen origen;
    private List<Etiqueta> etiquetas;
    private boolean visible;

    public Hecho() {
        this.etiquetas = new ArrayList<Etiqueta>();
    }

    public boolean tieneEtiqueta(Etiqueta etiqueta) {
        return this.etiquetas.contains(etiqueta);
    }

    public void addEtiqueta(Etiqueta etiqueta) {
        this.etiquetas.add(etiqueta);
    }
}