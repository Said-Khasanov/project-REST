package com.khasanov.project_rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageResponseTo {
    private Long id;
    private Long storyId;
    private String content;
}
