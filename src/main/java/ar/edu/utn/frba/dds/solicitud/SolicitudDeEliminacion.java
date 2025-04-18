package ar.edu.utn.frba.dds.solicitud;

import ar.edu.utn.frba.dds.contenido.Hecho;

import java.time.LocalDateTime;

public class SolicitudDeEliminacion {
    private Hecho hecho;
    private String descripcion;
    private EstadoDeSolicitud estadoDeSolicitud;
    private LocalDateTime fechaDeCreacion;


    public SolicitudDeEliminacion(Hecho hecho, String descripcion) {
        this.hecho = hecho;
        this.descripcion = descripcion;
        this.estadoDeSolicitud = EstadoDeSolicitud.PENDIENTE;
        this.fechaDeCreacion = LocalDateTime.now();
    }

    public void aceptarSolicitud() {
       estadoDeSolicitud = EstadoDeSolicitud.ACEPTADA;
       this.hecho.ocultarHecho();

    }

    public void rechazarSolicitud() {
        estadoDeSolicitud = EstadoDeSolicitud.RECHAZADA;
    }
}
