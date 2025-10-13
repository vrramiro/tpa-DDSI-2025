package ar.utb.ba.dsi.usuarios.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("id_usuario")
  private Long id;

  @Column(name = "nombre_usuario", nullable = false)
  @JsonProperty("nombre_usuario")
  private String nombreUsuario;

  @Column(name = "contrasenia", nullable = false)
  @JsonProperty("contrasenia")
  private String contrasenia;

  @Enumerated(EnumType.STRING)
  @Column(name = "rol", nullable = false)
  @JsonProperty("rol")
  private Rol rol;

  // Datos no obligatorios
  @Column(name = "fecha_nacimiento")
  @JsonProperty("fecha_nacimineto")
  private LocalDate fechaNacimiento;

  @Column(name = "nombre")
  @JsonProperty("nombre")
  private String nombre;

  @Column(name = "apellido")
  @JsonProperty("apellido")
  private String apellido;
}
