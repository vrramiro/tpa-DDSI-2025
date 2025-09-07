package ar.utb.ba.dsi.Normalizador.models.repository;

import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query("SELECT c FROM Categoria c WHERE :categoriaExterna MEMBER OF c.categoriasExternas")
    public Categoria findCategoriaByCategoriaExterna(String categoriaExterna);
}
