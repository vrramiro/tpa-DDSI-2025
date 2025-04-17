package ar.edu.utn.frba.dds.contenido;

import ar.edu.utn.frba.dds.criterio.Categoria;
import java.time.LocalDateTime;

public class Hecho {
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Ubicacion ubicacion;
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
    private Origen origen;
    private boolean visible;


    public void ocultarHecho() {
        this.visible = false;
    }
}
