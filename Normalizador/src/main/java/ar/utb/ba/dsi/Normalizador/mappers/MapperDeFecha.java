package ar.utb.ba.dsi.Normalizador.mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

public class MapperDeFecha {

  private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
      .appendPattern("yyyy-MM-dd")
      .optionalStart()
      .appendPattern("['T'][ ]HH:mm")
      .optionalStart()
      .appendPattern(":ss")
      .optionalEnd()
      .optionalStart()
      .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true) // 0 a 9 dígitos de fracción
      .optionalEnd()
      .optionalEnd()
      .toFormatter();

  public static LocalDateTime fromString(String fecha) {
    if (fecha == null || fecha.isBlank()) {
      return null;
    }
    try {
      return LocalDateTime.parse(fecha, FORMATTER);
    } catch (DateTimeParseException e) {
      try {
        LocalDate date = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return date.atStartOfDay();
      } catch (DateTimeParseException ex) {
        throw new RuntimeException("No se pudo parsear la fecha: " + fecha, ex);
      }
    }
  }

  public static String toString(LocalDateTime fecha) {
    if (fecha == null) return null;
    return fecha.format(FORMATTER);
  }
}
