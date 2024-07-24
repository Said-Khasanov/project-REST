package com.khasanov.project_rest.model.dto.response;

import com.khasanov.project_rest.model.entity.Creator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StoryResponseTo {
    private Long id;
    private Creator creator;
    private String title;
    private String content;
    private LocalDateTime created;
    private LocalDateTime modified;
}
