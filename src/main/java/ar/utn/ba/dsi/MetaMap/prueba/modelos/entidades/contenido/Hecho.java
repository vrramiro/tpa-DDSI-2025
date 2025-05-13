package ar.utn.ba.dsi.MetaMap.prueba.modelos.entidades.contenido;

import ar.utn.ba.dsi.MetaMap.prueba.modelos.entidades.criterio.Categoria;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Hecho {
    private Long id;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Ubicacion ubicacion;
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
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
