package ar.utb.ba.dsi.modelos.entidades.solicitud;

import ar.edu.utn.frba.dds.contenido.Hecho;
import ar.edu.utn.frba.dds.contenido.HechosEliminados;
import ar.edu.utn.frba.dds.usuarios.Administrador;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class SolicitudDeEliminacion {
    private Hecho hecho;
    private String descripcion;
    private EstadoDeSolicitud estadoDeSolicitud;
    private LocalDateTime fechaDeCreacion;
    private LocalDateTime fechaDeEvaluacion;
    @Setter
    private Administrador administradorQueEvaluo;

    private static Integer caracteresMinimos = 500;

    public SolicitudDeEliminacion(Hecho hecho, String descripcion) {
        this.hecho = hecho;
        setDescripcion(descripcion);
        this.estadoDeSolicitud = EstadoDeSolicitud.PENDIENTE;
        this.fechaDeCreacion = LocalDateTime.now();
    }

    public void aceptarSolicitud() {
        estadoDeSolicitud = EstadoDeSolicitud.ACEPTADA;
        this.hecho.setVisible(false);
        this.fechaDeEvaluacion = LocalDateTime.now();
        HechosEliminados.agregarHecho(hecho);
        setAdministradorQueEvaluo(administradorQueEvaluo);
    }

    public void rechazarSolicitud() {
        estadoDeSolicitud = EstadoDeSolicitud.RECHAZADA;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.length() < caracteresMinimos) {
            throw new IllegalArgumentException("La descripción debe tener minimo " + caracteresMinimos );
        }
        this.descripcion = descripcion;
    }
}
