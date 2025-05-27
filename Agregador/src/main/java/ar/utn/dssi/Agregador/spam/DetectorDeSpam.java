package ar.utn.dssi.Agregador.spam;


public class DetectorDeSpam {


  public static Boolean esSpam(String descripcion) {
    if (descripcion.length() > 300 || descripcion.contains("oferta")) {
      return true;
    } else return false;
  }
}