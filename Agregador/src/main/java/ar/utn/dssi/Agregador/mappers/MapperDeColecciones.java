package ar.utn.dssi.Agregador.mappers;

import ar.utn.dssi.Agregador.dto.input.ColeccionInputDTO;
import ar.utn.dssi.Agregador.dto.output.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.dto.output.CriterioDePertenenciaOutputDTO;
import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.fuente.TipoFuente;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MapperDeColecciones {
  public static ColeccionOutputDTO coleccionOutputDTOFromColeccion(Coleccion coleccion) {
    ColeccionOutputDTO coleccionDto = new ColeccionOutputDTO();

    coleccionDto.setHandle(coleccion.getHandle());
    coleccionDto.setTitulo(coleccion.getTitulo());
    coleccionDto.setDescripcion(coleccion.getDescripcion());

    List<CriterioDePertenenciaOutputDTO> criterios = coleccion.getCriterios().stream()
        .map(MapperDeCriterio::criterioDePertenenciaOutputDTOFromCriterio)
        .collect(Collectors.toList());
    coleccionDto.setCriterios(criterios);

    Set<TipoFuente> fuentes = coleccion.getFuentes().stream()
        .map(fuente -> fuente.getTipoFuente().getTipoFuente())
        .collect(Collectors.toSet());
    coleccionDto.setFuentes(fuentes);

    coleccion.setConsenso(coleccion.getConsenso());

    return coleccionDto;
  }

  //carga solo datos basicos, las fuentes no son obtenibles a este nivel las debe cargar el service => vienen del repo
  //el handle solo se carga en caso de ser necesario
  public static Coleccion coleccionFromColeccionInputDTO(ColeccionInputDTO input) {
    Coleccion coleccion = new Coleccion();
    coleccion.setTitulo(input.getTitulo());
    coleccion.setDescripcion(input.getDescripcion());
    List<CriterioDePertenencia> criterios = input.getCriteriosDePertenecias().stream()
        .map(MapperDeCriterio::criterioFromCriterioInputDTO)
        .collect(Collectors.toCollection(ArrayList::new));
    coleccion.setCriterios(criterios);
    coleccion.setHechos(new ArrayList<>());
    coleccion.setFuentes(new ArrayList<>());
    coleccion.setActualizada(Boolean.FALSE);
    coleccion.setConsenso(input.getConsenso());
    return coleccion;
  }
}
