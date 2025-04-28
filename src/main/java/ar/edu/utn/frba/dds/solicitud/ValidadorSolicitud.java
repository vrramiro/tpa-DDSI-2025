package ar.edu.utn.frba.dds.solicitud;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidadorSolicitud {
    private static Integer caracteresMinimos = 500;

    public static void validarSolicitud(String descripcion) {
        if (descripcion == null || descripcion.length() < caracteresMinimos) {
            throw new IllegalArgumentException("Descripcion no cumple con la cantidad de caracteres minimas requeridas");
        }
    }
}
