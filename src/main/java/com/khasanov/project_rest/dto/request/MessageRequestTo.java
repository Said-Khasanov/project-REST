package com.khasanov.project_rest.dto.request;

import com.khasanov.project_rest.entity.Story;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageRequestTo {
    private Long id;
    private Story story;
    private String content;
}
