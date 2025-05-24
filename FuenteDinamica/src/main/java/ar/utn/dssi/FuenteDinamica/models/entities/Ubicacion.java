package ar.utn.dssi.FuenteDinamica.models.entities;

import lombok.Getter;

@Getter
public class Ubicacion {
  private double latitud;
  private double longitud;

  public Ubicacion(double latitud, double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }
}