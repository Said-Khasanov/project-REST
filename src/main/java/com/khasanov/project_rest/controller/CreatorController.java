package com.khasanov.project_rest.controller;

import com.khasanov.project_rest.model.dto.request.CreatorRequestTo;
import com.khasanov.project_rest.model.dto.response.CreatorResponseTo;
import com.khasanov.project_rest.service.CreatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v${application.api.version}/creators")
public class CreatorController {
    private final CreatorService creatorService;

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<CreatorResponseTo> findById(@RequestParam Long id) {
        CreatorResponseTo creatorResponseTo = creatorService.findById(id);
        return new ResponseEntity<>(creatorResponseTo, HttpStatus.OK);
    }

    public ResponseEntity<CreatorResponseTo> createCreator(CreatorRequestTo creatorRequestTo) {
        CreatorResponseTo creatorResponseTo = creatorService.createCreator(creatorRequestTo);
        return new ResponseEntity<>(creatorResponseTo, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteById(@RequestParam Long id) {
        creatorService.deleteCreator(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
