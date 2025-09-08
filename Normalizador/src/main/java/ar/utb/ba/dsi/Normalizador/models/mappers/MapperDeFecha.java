package ar.utb.ba.dsi.Normalizador.models.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MapperDeFecha {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static LocalDateTime fromString(String fecha) {
        if (fecha == null || fecha.isBlank()) {
            return null;
        }
        return LocalDateTime.parse(fecha, FORMATTER);
    }

    public static String toString(LocalDateTime fecha) {
        if (fecha == null) {
            return null;
        }
        return fecha.format(FORMATTER);
    }
}
