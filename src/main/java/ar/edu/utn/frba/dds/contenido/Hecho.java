package ar.edu.utn.frba.dds.contenido;

import ar.edu.utn.frba.dds.criterio.Categoria;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Hecho {
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Ubicacion ubicacion;
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
    private Origen origen;
    private List<Etiqueta> etiquetas;
    private boolean visible;

    public Hecho(String titulo, String descripcion, Categoria categoria, Ubicacion ubicacion,
                 LocalDateTime fechaAcontecimiento) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
        this.fechaAcontecimiento = fechaAcontecimiento;
        this.etiquetas = new ArrayList<Etiqueta>();
    }

    public boolean tieneEtiqueta(Etiqueta etiqueta) {
        return this.etiquetas.contains(etiqueta);
    }
}
