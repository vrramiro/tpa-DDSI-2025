package ar.utn.ba.dsi.MetaMap.modelos.repositorios;

import ar.utn.ba.dsi.MetaMap.modelos.entidades.contenido.Hecho;

import java.util.List;

public interface IHechosRepositorio {
    public Hecho buscarHechoPorId(Long id);
    public void save(Hecho hecho);
    public List<Hecho> findall();
}
