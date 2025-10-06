package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.error.CriterioDistintoTipo;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.CriterioDePertenenciaInputDTO;
import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.TipoCriterio;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioFechaDesde;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioFechaHasta;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioPorCategoria;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioPorProvincia;
import ar.utn.dssi.Agregador.models.mappers.MapperDeCriterio;
import ar.utn.dssi.Agregador.services.ICriterioDePertenenciaService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CriterioDePertenenciaService implements ICriterioDePertenenciaService {
  @Override
  public Boolean actualizarCriterios(Coleccion coleccion, List<CriterioDePertenenciaInputDTO> criteriosNuevos) {
    Map<Long, CriterioDePertenencia> criteriosOriginales = coleccion.getCriterios()
        .stream()
        .collect(Collectors.toMap(CriterioDePertenencia::getId, criterio -> criterio));

    boolean cambiaronCriterios = false;

    List<CriterioDePertenencia> criteriosActualizados = new ArrayList<>();

    for(CriterioDePertenenciaInputDTO criterioInput : criteriosNuevos) {
      if(criterioInput.getId() == null) {
        cambiaronCriterios = true;
        criteriosActualizados.add(MapperDeCriterio.criterioFromCriterioInputDTO(criterioInput)); //es un criterio NUEVO
      } else {
        CriterioDePertenencia criterioAActualizar = criteriosOriginales.remove(criterioInput.getId()); //obtengo el original posiblemente a actualizar

        if(criterioAActualizar == null) {
          throw new IllegalArgumentException("No hay criterio cargado con id: " + criterioInput.getId() + " para la coleccion con handle: " + coleccion.getHandle());
        }

        cambiaronCriterios = this.actualizarCriterio(criterioAActualizar, criterioInput);

        criteriosActualizados.add(criterioAActualizar);
      }
    }

    if(!criteriosOriginales.isEmpty()) cambiaronCriterios = true; //quedaron criterios originales que no están en la lista nueva, por lo que se eliminaron

    if(cambiaronCriterios) coleccion.actualizarCriterios(criteriosActualizados); //no quiero pisar innecesariamente la lista de criterios si no hubo cambios

    return cambiaronCriterios;
  }

  // Retorna true si se actualizó el criterio, false si no hubo cambios
  private Boolean actualizarCriterio(CriterioDePertenencia criterioOriginal, CriterioDePertenenciaInputDTO criterioInput) {
    TipoCriterio tipoNuevo = MapperDeCriterio.tipoCriterioFromString(criterioInput.getTipo());
    TipoCriterio tipoOriginal = criterioOriginal.getTipoCriterio();

    if(!tipoNuevo.equals(tipoOriginal)) {
      throw new CriterioDistintoTipo("El tipo de criterio no coincide con el original. Original: "
          + tipoOriginal + ", Nuevo: " + tipoNuevo);
    }

    if(criterioInput.getValor() == null || criterioInput.getValor().isEmpty())
      throw new IllegalArgumentException("El valor del criterio no puede ser nulo o vacío");

    if(criterioOriginal.mismoValor(criterioInput.getValor())) return false;

    switch(tipoNuevo) {
      case PROVINCIA -> ((CriterioPorProvincia) criterioOriginal).setProvincia(criterioInput.getValor());
      case CATEGORIA -> ((CriterioPorCategoria) criterioOriginal).setCategoria(criterioInput.getValor());
      case FECHA_DESDE -> ((CriterioFechaDesde) criterioOriginal).setFechaDesde(this.parsearFecha(criterioInput.getValor()));
      case FECHA_HASTA -> ((CriterioFechaHasta) criterioOriginal).setFechaHasta(this.parsearFecha(criterioInput.getValor()));
      default -> throw new IllegalArgumentException("Tipo de criterio no reconocido: " + tipoNuevo);
    }

    return true;
  }

  private LocalDate parsearFecha(String fecha) {
    try {
      LocalDate fechaNueva = LocalDate.parse(fecha);

      if(fechaNueva.isAfter(LocalDate.now())) throw new IllegalArgumentException("La fecha no puede ser futura: " + fecha);

      return fechaNueva;
    } catch (Exception e) {
      throw new IllegalArgumentException("Formato de fecha inválido. Se esperaba AAAA-MM-DD, se recibió: " + fecha);
    }
  }
}
