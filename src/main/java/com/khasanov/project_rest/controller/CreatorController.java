package com.khasanov.project_rest.controller;

import com.khasanov.project_rest.dto.request.CreatorRequestTo;
import com.khasanov.project_rest.dto.response.CreatorResponseTo;
import com.khasanov.project_rest.service.CreatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${spring.application.api-v1-prefix}/creators")
public class CreatorController {
    private final CreatorService creatorService;

    @GetMapping
    public ResponseEntity<List<CreatorResponseTo>> findAll() {
        List<CreatorResponseTo> creatorResponseToList = creatorService.findAll();
        return ResponseEntity.ok(creatorResponseToList);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<CreatorResponseTo> findById(@PathVariable Long id) {
        CreatorResponseTo creatorResponseTo = creatorService.findById(id);
        return ResponseEntity.ok(creatorResponseTo);
    }

    @PostMapping
    public ResponseEntity<CreatorResponseTo> save(@Valid @RequestBody CreatorRequestTo creatorRequestTo) {
        CreatorResponseTo creatorResponseTo = creatorService.save(creatorRequestTo);
        return ResponseEntity.status(HttpStatus.CREATED).body(creatorResponseTo);
    }

    @PutMapping
    public ResponseEntity<CreatorResponseTo> update(@Valid @RequestBody CreatorRequestTo creatorRequestTo) {
        CreatorResponseTo creatorResponseTo = creatorService.update(creatorRequestTo);
        return ResponseEntity.ok(creatorResponseTo);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        creatorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
