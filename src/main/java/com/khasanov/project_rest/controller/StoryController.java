package com.khasanov.project_rest.controller;

import com.khasanov.project_rest.dto.request.StoryRequestTo;
import com.khasanov.project_rest.dto.response.StoryResponseTo;
import com.khasanov.project_rest.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${spring.application.api-prefix}/storys")
public class StoryController {
    private final StoryService storyService;

    @GetMapping
    public ResponseEntity<List<StoryResponseTo>> findAll() {
        List<StoryResponseTo> storyResponseToList = storyService.findAll();
        return ResponseEntity.ok(storyResponseToList);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<StoryResponseTo> findById(@PathVariable Long id) {
        StoryResponseTo storyResponseTo = storyService.findById(id);
        return ResponseEntity.ok(storyResponseTo);
    }

    @PostMapping
    public ResponseEntity<StoryResponseTo> create(@RequestBody StoryRequestTo storyRequestTo) {
        StoryResponseTo storyResponseTo = storyService.save(storyRequestTo);
        return ResponseEntity.status(HttpStatus.CREATED).body(storyResponseTo);
    }

    @PutMapping
    public ResponseEntity<StoryResponseTo> update(@RequestBody StoryRequestTo storyRequestTo) {
        StoryResponseTo storyResponseTo = storyService.update(storyRequestTo);
        return ResponseEntity.ok(storyResponseTo);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        storyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
