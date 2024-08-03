package com.khasanov.project_rest.dto.response;

import com.khasanov.project_rest.entity.Story;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageResponseTo {
    private Long id;
    private Story story;
    private String content;
}
