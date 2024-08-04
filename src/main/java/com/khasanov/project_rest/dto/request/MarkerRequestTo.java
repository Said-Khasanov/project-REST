package com.khasanov.project_rest.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MarkerRequestTo {
    private Long id;

    @NotNull
    @Size(min = 2, max = 32)
    private String name;
}
