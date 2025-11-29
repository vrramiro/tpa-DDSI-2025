package ar.utn.dssi.app_web.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageResponseDTO<T>{
    @JsonProperty("content")
    private List<T> content = new ArrayList<>();

    @JsonProperty("totalPages")
    private int totalPages;

    @JsonProperty("totalElements")
    private long totalElements;

    @JsonProperty("number")
    private int number;

    @JsonProperty("size")
    private int size;

    @JsonProperty("first")
    private boolean first;

    @JsonProperty("last")
    private boolean last;
}
