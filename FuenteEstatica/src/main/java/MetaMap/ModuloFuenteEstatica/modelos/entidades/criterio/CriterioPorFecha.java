package ar.utb.ba.dsi.modelos.entidades.criterio;

import ar.edu.utn.frba.dds.contenido.Hecho;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class CriterioPorFecha implements CriterioDePertenecia {
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    public CriterioPorFecha(LocalDate desde,LocalDate hasta) {
        this.fechaDesde = desde;
        this.fechaHasta = hasta;
    }

    public Boolean hechoLoCumple(Hecho unHecho) {
        LocalDate fechaDeAcontecimiento = unHecho.getFechaAcontecimiento().toLocalDate();

        return fechaDeAcontecimiento.isAfter(this.fechaDesde) && fechaDeAcontecimiento.isBefore(this.fechaHasta);
    }
}
