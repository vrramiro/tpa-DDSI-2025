package ar.utn.dssi.app_web.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor; // Importante para Jackson

import java.util.ArrayList;
import java.util.List;

@Data
public class PageResponseDTO<T> {

    @JsonProperty("content")
    private List<T> content = new ArrayList<>();

    // Mapeamos el objeto anidado "page" del JSON
    @JsonProperty("page")
    private PageMetadata page;

    // --- Métodos "puente" para que tu Controller y Service sigan funcionando igual ---

    public int getTotalPages() {
        return page != null ? page.getTotalPages() : 0;
    }

    public long getTotalElements() {
        return page != null ? page.getTotalElements() : 0;
    }

    public int getNumber() {
        return page != null ? page.getNumber() : 0;
    }

    public int getSize() {
        return page != null ? page.getSize() : 0;
    }

    // Calculamos estos manualmente porque a veces "page" no trae first/last explícitos
    public boolean isFirst() {
        return getNumber() == 0;
    }

    public boolean isLast() {
        return getNumber() >= (getTotalPages() - 1);
    }

    // Clase interna para mapear la info que viene dentro de "page"
    @Data
    @NoArgsConstructor
    public static class PageMetadata {
        private int size;
        private long totalElements;
        private int totalPages;
        private int number;
    }
}