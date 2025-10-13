package ar.utb.ba.dsi.usuarios.models.repositories;

import ar.utb.ba.dsi.usuarios.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findUsuarioByNombreUsuario(String nombreUsuario);
}
