package ar.utb.ba.dsi.estadisticas.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="estadistica")
public class Estadistica {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_estadistica", nullable = false)
    private TipoEstadistica tipo;

    @Column(name = "valor", nullable = false)
    private Long valor;

    @Column(name = "resultado", nullable = false)
    private String resultado;

    @Column(name = "fecha_calculo", nullable = false)
    private LocalDateTime fechaDeCalculo;
}
