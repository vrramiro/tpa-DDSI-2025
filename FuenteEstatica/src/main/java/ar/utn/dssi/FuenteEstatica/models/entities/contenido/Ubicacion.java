package ar.utn.dssi.FuenteEstatica.models.entities.contenido;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ubicacion {
    private Double latitud;
    private Double longitud;
    private String pais;
    private String ciudad;
    private String provincia;

    public Boolean invalida() {
        return this.longitud == null || this.latitud == null ||
                this.ciudad == null || this.provincia == null || this.pais == null;
    }
}
