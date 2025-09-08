package ar.utb.ba.dsi.estadisticas.models.entities;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class Hecho {
    private String categoria;
    private String provincia;
    private LocalDateTime fechaAcontecimiento;
}
