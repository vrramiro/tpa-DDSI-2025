package ar.edu.utn.frba.dds.solicitud;

import ar.edu.utn.frba.dds.contenido.Hecho;
import ar.edu.utn.frba.dds.contenido.HechosEliminados;
import ar.edu.utn.frba.dds.usuarios.Administrador;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class SolicitudDeEliminacion {
    private Hecho hecho;
    private String descripcion;
    private EstadoDeSolicitud estadoDeSolicitud;
    private LocalDateTime fechaDeCreacion;
    private LocalDateTime fechaDeEvaluacion;
    private Administrador administradorQueEvaluo;
    private static Integer cantidadMinimaCaracteres;

    public SolicitudDeEliminacion(Hecho hecho, String descripcion) {
        this.hecho = hecho;
        this.descripcion = descripcion;
        this.estadoDeSolicitud = EstadoDeSolicitud.PENDIENTE;
        this.fechaDeCreacion = LocalDateTime.now();
        this.cantidadMinimaCaracteres = 500;
    }

    public void aceptarSolicitud() {
        if(descripcion.length() > cantidadMinimaCaracteres) {
            estadoDeSolicitud = EstadoDeSolicitud.ACEPTADA;
            this.hecho.setVisible(false);
            this.fechaDeEvaluacion = LocalDateTime.now();
            HechosEliminados.agregarHecho(hecho);
            setAdministradorQueEvaluo(administradorQueEvaluo);
        } else rechazarSolicitud();
    }

    public void rechazarSolicitud() {
        estadoDeSolicitud = EstadoDeSolicitud.RECHAZADA;
    }
}
