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

    private Long coleccionId;

    private Long categotiaId; // representa a la categoria a la que pertenece la estadistica => tipo cant_hechos_categorias

    @Column(name = "valor", nullable = false)
    private Long valor;

    @Column(name = "clave", nullable = false)
    private String clave; //puede llevar el nombre de la provincia, categoria, hora, dependiendo del tipo de estadistica

    @Column(name = "fecha_calculo", nullable = false)
    private LocalDateTime fechaDeCalculo;
}
