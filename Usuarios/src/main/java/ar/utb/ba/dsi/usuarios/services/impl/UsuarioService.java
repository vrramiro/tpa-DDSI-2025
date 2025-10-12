package ar.utb.ba.dsi.usuarios.services.impl;

import ar.utb.ba.dsi.usuarios.dto.UsuarioDTO;
import ar.utb.ba.dsi.usuarios.error.UsuarioDatosFaltantes;
import ar.utb.ba.dsi.usuarios.error.UsuarioDuplicadoExcepcion;
import ar.utb.ba.dsi.usuarios.models.entities.Usuario;
import ar.utb.ba.dsi.usuarios.models.repositories.IUsuarioRepository;
import ar.utb.ba.dsi.usuarios.services.IUsuarioService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void crearUsuario(UsuarioDTO usuarioDTO) {
        validarDatosBasicos(usuarioDTO);
        validarDuplicidadUsuario(usuarioDTO);

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setContrasenia(bCryptPasswordEncoder.encode(usuarioDTO.getContrasenia()));
        usuario.setRol(usuarioDTO.getRol());

        usuarioRepository.save(usuario);
    }

    private void validarDuplicidadUsuario(UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findUsuarioByNombreUsuario(usuarioDTO.getNombreUsuario());
        if (usuarioExistente.isPresent()) {
            throw new UsuarioDuplicadoExcepcion("El nombre de usuario no esta disponible: " + usuarioDTO.getNombreUsuario());
        }
    }

    private void validarDatosBasicos(UsuarioDTO usuarioDTO) {
        UsuarioDatosFaltantes usuarioDatosFaltantes = new UsuarioDatosFaltantes("Errores de validación");
        boolean tieneErrores = false;

        if (usuarioDTO.getNombreUsuario() == null || usuarioDTO.getNombreUsuario().isEmpty()) {
            usuarioDatosFaltantes.addFieldError("nombreUsuario", "El nombre de usuario es obligatorio");
            tieneErrores = true;
        }

        if (usuarioDTO.getContrasenia() == null || usuarioDTO.getContrasenia().isEmpty()) {
            usuarioDatosFaltantes.addFieldError("contrasenia", "La contrasenia es obligatoria");
            tieneErrores = true;
        }

        if (usuarioDTO.getRol() == null) {
            usuarioDatosFaltantes.addFieldError("rol", "El rol es obligatorio");
            tieneErrores = true;
        }

        if (tieneErrores) {
            throw usuarioDatosFaltantes;
        }
    }
}
