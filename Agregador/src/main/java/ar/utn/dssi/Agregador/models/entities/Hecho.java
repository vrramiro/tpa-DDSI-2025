package ar.utn.dssi.Agregador.models.entities;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hecho {
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Ubicacion ubicacion;
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
    private List<MultipartFile> contenidoMultimedia;
    private Long IdHecho;
    private Origen origen;
    private Long idOrigen;
    private Long idFuente;
    private boolean visible;

    @Builder.Default
    private List<Etiqueta> etiquetas = new ArrayList<>();

    public boolean tieneEtiqueta(Etiqueta etiqueta) {
        return this.etiquetas.contains(etiqueta);
    }

    public void addEtiqueta(Etiqueta etiqueta) {
        this.etiquetas.add(etiqueta);
    }

    //no todos los atributos porque por que compararias la descripcion
    public Boolean mismoHecho(Hecho otroHecho) {
        return this.getTitulo().equals(otroHecho.getTitulo()) &&
            this.getCategoria().equals(otroHecho.getCategoria()) &&
            this.getUbicacion().equals(otroHecho.getUbicacion()) &&
            this.getFechaAcontecimiento().equals(otroHecho.getFechaAcontecimiento());
    }

    public boolean mismoMismoTitulo(Hecho otroHecho) { return this.getTitulo().equals(otroHecho.getTitulo()); }

    public boolean mismosAtributos(Hecho otroHecho) {
        return this.getDescripcion().equals(otroHecho.getDescripcion())
            || this.getCategoria().equals(otroHecho.getCategoria())
            || this.getUbicacion().equals(otroHecho.getUbicacion())
            || this.getFechaAcontecimiento().equals(otroHecho.getFechaAcontecimiento());
    }

    public boolean distintaFuente(Hecho hecho) { return (!Objects.equals(hecho.getIdFuente(), this.getIdFuente())); }

    public boolean noConsensuado() { return true; }
}