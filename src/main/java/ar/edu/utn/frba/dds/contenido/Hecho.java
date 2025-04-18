package ar.edu.utn.frba.dds.contenido;

import ar.edu.utn.frba.dds.criterio.Categoria;
import ar.edu.utn.frba.dds.contenido.HechosEliminados;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @Getter private List<Etiqueta> etiquetas;
    private boolean visible;


    public Hecho(String titulo, String descripcion, Categoria categoria, Ubicacion ubicacion,
                 LocalDateTime fechaAcontecimiento, LocalDateTime fechaCarga, Origen origen, List<Etiqueta> etiquetas, boolean visible) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
        this.fechaAcontecimiento = fechaAcontecimiento;
        this.fechaCarga = fechaCarga;
        this.origen = origen;
        this.etiquetas = etiquetas;
        this.visible = visible;
    }
}
