package ar.utn.dssi.Estadisticas.models.entities.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class Hecho {
    private String categoria;
    private String provincia;
    private LocalDateTime fechaAcontecimiento;
}
