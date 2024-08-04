package com.khasanov.project_rest.controller;

import com.khasanov.project_rest.dto.request.MessageRequestTo;
import com.khasanov.project_rest.dto.response.MessageResponseTo;
import com.khasanov.project_rest.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${spring.application.api-prefix}/messages")
public class MessageController {
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageResponseTo>> findAll() {
        List<MessageResponseTo> messageResponseToList = messageService.findAll();
        return ResponseEntity.ok(messageResponseToList);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<MessageResponseTo> findById(@PathVariable Long id) {
        MessageResponseTo messageResponseTo = messageService.findById(id);
        return ResponseEntity.ok(messageResponseTo);
    }

    @PostMapping
    public ResponseEntity<MessageResponseTo> save(@Valid @RequestBody MessageRequestTo messageRequestTo) {
        MessageResponseTo messageResponseTo = messageService.save(messageRequestTo);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageResponseTo);
    }

    @PutMapping
    public ResponseEntity<MessageResponseTo> update(@Valid @RequestBody MessageRequestTo messageRequestTo) {
        MessageResponseTo messageResponseTo = messageService.update(messageRequestTo);
        return ResponseEntity.ok(messageResponseTo);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        messageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
