package com.khasanov.project_rest.controller;

import com.khasanov.project_rest.dto.request.MarkerRequestTo;
import com.khasanov.project_rest.dto.response.MarkerResponseTo;
import com.khasanov.project_rest.service.MarkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${spring.application.api-prefix}/markers")
public class MarkerController {
    private final MarkerService markerService;

    @GetMapping
    public ResponseEntity<List<MarkerResponseTo>> findAll() {
        List<MarkerResponseTo> markerResponseToList = markerService.findAll();
        return ResponseEntity.ok(markerResponseToList);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<MarkerResponseTo> findById(@PathVariable Long id) {
        MarkerResponseTo markerResponseTo = markerService.findById(id);
        return ResponseEntity.ok(markerResponseTo);
    }

    @PostMapping
    public ResponseEntity<MarkerResponseTo> create(@Valid @RequestBody MarkerRequestTo markerRequestTo) {
        MarkerResponseTo markerResponseTo = markerService.save(markerRequestTo);
        return ResponseEntity.status(HttpStatus.CREATED).body(markerResponseTo);
    }

    @PutMapping
    public ResponseEntity<MarkerResponseTo> update(@Valid @RequestBody MarkerRequestTo markerRequestTo) {
        MarkerResponseTo markerResponseTo = markerService.update(markerRequestTo);
        return ResponseEntity.ok(markerResponseTo);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        markerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
