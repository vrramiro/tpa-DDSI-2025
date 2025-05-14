package ar.utb.ba.dsi.modelos.repositorios;

import ar.utn.ba.dsi.MetaMap.metaMap.modelos.entidades.contenido.Hecho;

import java.util.List;

public interface IHechosRepositorio {
    public Hecho buscarHechoPorId(Long id);
    public void save(Hecho hecho);
    public List<Hecho> findall();
}
