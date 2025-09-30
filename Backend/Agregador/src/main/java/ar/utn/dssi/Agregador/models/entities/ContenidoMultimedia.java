package ar.utn.dssi.Agregador.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "contenidos_multimedia")
public class ContenidoMultimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contenido_multimedia_id")
    private Long id;

    @Column(nullable = false, name = "url")
    private String url;

    @Column(nullable = false, name = "formato")
    private String formato;

    @Column(nullable = false, name = "tamanio")
    private Long tamanio;
}
