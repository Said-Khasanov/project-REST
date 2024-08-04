package com.khasanov.project_rest.service;

import com.khasanov.project_rest.dto.request.MessageRequestTo;
import com.khasanov.project_rest.dto.response.MessageResponseTo;
import com.khasanov.project_rest.entity.Message;
import com.khasanov.project_rest.entity.Story;
import com.khasanov.project_rest.mapper.MessageMapper;
import com.khasanov.project_rest.repository.MessageRepository;
import com.khasanov.project_rest.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNullElse;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final StoryRepository storyRepository;
    private final MessageMapper messageMapper;

    public List<MessageResponseTo> findAll() {
        return messageRepository.findAll().stream()
                .map(messageMapper::toMessageResponseTo)
                .toList();
    }

    public MessageResponseTo findById(Long id) {
        Message message = messageRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return messageMapper.toMessageResponseTo(message);
    }

    public MessageResponseTo save(MessageRequestTo messageRequestTo) {
        Message message = messageMapper.toEntity(messageRequestTo);
        Message messageInDb = messageRepository.save(message);
        return messageMapper.toMessageResponseTo(messageInDb);
    }

    public MessageResponseTo update(MessageRequestTo messageRequestTo) {
        MessageResponseTo messageResponseTo = findById(messageRequestTo.getId());
        Message message = messageMapper.toEntity(messageResponseTo);
        message.setContent(requireNonNullElse(messageRequestTo.getContent(), message.getContent()));
        Long storyId = messageRequestTo.getStoryId();
        if (storyId != null) {
            Story story = storyRepository.findById(storyId).orElseThrow(NoSuchElementException::new);
            message.setStory(story);
        }
        Message updatedMessage = messageRepository.save(message);
        return messageMapper.toMessageResponseTo(updatedMessage);
    }

    public void deleteById(Long id) {
        findById(id);
        messageRepository.deleteById(id);
    }
}
