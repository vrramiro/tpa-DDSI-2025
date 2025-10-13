package ar.utb.ba.dsi.usuarios.error;

import ar.utb.ba.dsi.usuarios.dto.UsuarioDTO;

public class UsuarioDuplicadoExcepcion  extends RuntimeException{
    public UsuarioDuplicadoExcepcion(String message) {super(message);}
}
