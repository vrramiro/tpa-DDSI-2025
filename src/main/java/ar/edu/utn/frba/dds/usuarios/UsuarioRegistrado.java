package ar.edu.utn.frba.dds.usuarios;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioRegistrado {
    protected String nombre;
    protected String apellido;
    protected int edad;

    public UsuarioRegistrado(String nombre, String apellido, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }


}
