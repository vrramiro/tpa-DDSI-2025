package ar.utn.dssi.Agregador.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Ubicacion {
    @Column(name = "pais")
    private String pais;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;
}