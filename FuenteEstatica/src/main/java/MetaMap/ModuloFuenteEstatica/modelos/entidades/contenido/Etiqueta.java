package ar.utb.ba.dsi.modelos.entidades.contenido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Etiqueta {
  private String nombre;

  public Etiqueta(String nombre) {
    this.nombre = nombre;
  }
}
