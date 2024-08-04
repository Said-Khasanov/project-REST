package com.khasanov.project_rest.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageRequestTo {
    private Long id;

    private Long storyId;

    @Size(min = 2, max = 2048)
    private String content;
}
