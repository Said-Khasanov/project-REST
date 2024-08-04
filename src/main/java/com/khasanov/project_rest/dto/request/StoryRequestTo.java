package com.khasanov.project_rest.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StoryRequestTo {
    private Long id;

    private Long creatorId;

    @Size(min = 2, max = 64)
    private String title;

    @Size(min = 4, max = 2048)
    private String content;

    private LocalDateTime created;

    private LocalDateTime modified;
}
