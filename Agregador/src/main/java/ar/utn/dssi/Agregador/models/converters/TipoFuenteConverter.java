package ar.utn.dssi.Agregador.models.converters;

import ar.utn.dssi.Agregador.models.entities.fuente.ITipoFuente;
import ar.utn.dssi.Agregador.models.entities.fuente.impl.FuenteDinamica;
import ar.utn.dssi.Agregador.models.entities.fuente.impl.FuenteEstatica;
import ar.utn.dssi.Agregador.models.entities.fuente.impl.FuenteProxy;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoFuenteConverter implements AttributeConverter<ITipoFuente, String> {
  @Override
  public String convertToDatabaseColumn(ITipoFuente tipo) {

    if (tipo instanceof FuenteDinamica) {
      return "DINAMICA";
    } else if (tipo instanceof FuenteEstatica) {
      return "ESTATICA";
    } else if (tipo instanceof FuenteProxy) {
      return "PROXY";
    }

    return null;
  }

  @Override
  public ITipoFuente convertToEntityAttribute(String dbData) {
    return switch (dbData) {
      case "DINAMICA" -> new FuenteDinamica();
      case "ESTATICA" -> new FuenteEstatica();
      case "PROXY" -> new FuenteProxy();
      default -> null;
    };
  }
}
