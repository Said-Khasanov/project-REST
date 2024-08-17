package com.khasanov.project_rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StoryResponseTo {
    private Long id;
    private Long creatorId;
    private String title;
    private String content;
    private List<MarkerResponseTo> markers = new ArrayList<>();
    private LocalDateTime created;
    private LocalDateTime modified;
}
