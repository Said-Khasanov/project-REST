package com.khasanov.project_rest.controller;

import com.khasanov.project_rest.model.dto.request.CreatorRequestTo;
import com.khasanov.project_rest.model.dto.response.CreatorResponseTo;
import com.khasanov.project_rest.service.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${spring.application.api-prefix}/creators")
public class CreatorController {
    private final CreatorService creatorService;

    @GetMapping
    public ResponseEntity<List<CreatorResponseTo>> getAllCreators() {
        List<CreatorResponseTo> creatorResponseToList = creatorService.findAll();
        return ResponseEntity.ok(creatorResponseToList);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<CreatorResponseTo> findById(@PathVariable Long id) {
        CreatorResponseTo creatorResponseTo = creatorService.findById(id);
        return ResponseEntity.ok(creatorResponseTo);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatorResponseTo> createCreator(@RequestBody CreatorRequestTo creatorRequestTo) {
        CreatorResponseTo creatorResponseTo = creatorService.createCreator(creatorRequestTo);
        return ResponseEntity.status(HttpStatus.CREATED).body(creatorResponseTo);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        creatorService.deleteCreator(id);
        return ResponseEntity.noContent().build();
    }
}
