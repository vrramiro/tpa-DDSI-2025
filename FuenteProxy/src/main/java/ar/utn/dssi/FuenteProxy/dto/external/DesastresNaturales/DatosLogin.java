package ar.utn.dssi.FuenteProxy.dto.external.DesastresNaturales;

import lombok.Data;

@Data
public class DatosLogin {
  private String email;
  private String password;

  public DatosLogin(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
