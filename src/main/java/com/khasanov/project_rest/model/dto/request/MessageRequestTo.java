package com.khasanov.project_rest.model.dto.request;

import com.khasanov.project_rest.model.entity.Story;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageRequestTo {
    private Story story;
    private String content;
}
