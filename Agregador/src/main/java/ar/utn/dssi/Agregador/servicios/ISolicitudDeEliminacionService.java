package ar.utn.dssi.Agregador.servicios;

public interface ISolicitudDeEliminacionService {
    public void obtenerSolicitudes();
    public void obtnerSolicitudPorId(Long idSolicitud);
    //public void obtenerSolicitudesPorEstado(EstadoDeSolicitud estado);
    //public void aceptarSolicitud(SolicitudInputDTO solicitud);
    //public void rechazarSolicitud(SolicitudInputDTO solicitud);
}
