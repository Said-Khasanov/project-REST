package com.khasanov.project_rest.service;

import com.khasanov.project_rest.dto.request.MessageRequestTo;
import com.khasanov.project_rest.dto.response.MessageResponseTo;
import com.khasanov.project_rest.entity.Message;
import com.khasanov.project_rest.entity.Story;
import com.khasanov.project_rest.mapper.MessageMapper;
import com.khasanov.project_rest.mapper.MessageMapperImpl;
import com.khasanov.project_rest.repository.MessageRepository;
import com.khasanov.project_rest.repository.StoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MessageServiceTest {

    private MessageService messageService;
    private MessageRepository messageRepository;
    private StoryRepository storyRepository;
    private static final MessageMapper messageMapper = new MessageMapperImpl();

    @BeforeEach
    void setUp() {
        messageRepository = Mockito.mock(MessageRepository.class);
        storyRepository = Mockito.mock(StoryRepository.class);
        messageService = new MessageService(messageRepository, storyRepository, messageMapper);
    }

    @Test
    void findAll() {
        Message message1 = Message.builder()
                .id(1L)
                .content("some content 1")
                .build();
        Message message2 = Message.builder()
                .id(2L)
                .content("some content 2")
                .build();
        List<Message> messages = List.of(message1, message2);
        Mockito.doReturn(messages).when(messageRepository).findAll();
        List<MessageResponseTo> expected = messages
                .stream()
                .map(messageMapper::toMessageResponseTo)
                .toList();
        List<MessageResponseTo> actual = messageService.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findById() {
        long id = 1L;
        Message message = Message.builder()
                .id(id)
                .content("some content 1")
                .build();
        Mockito.doReturn(Optional.of(message)).when(messageRepository).findById(id);
        MessageResponseTo expected = messageMapper.toMessageResponseTo(message);
        MessageResponseTo actual = messageService.findById(id);
        assertEquals(expected, actual);
    }

    @Test
    void whenFindByNonExistingId_thenThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> messageService.findById(-1L));
    }

    @Test
    void findByStoryId() {
        long storyId = 1L;
        Story story = Story.builder().id(storyId).build();
        Message message1 = Message.builder()
                .id(1L)
                .content("some content 1")
                .story(story)
                .build();
        Message message2 = Message.builder()
                .id(2L)
                .content("some content 2")
                .story(story)
                .build();
        List<Message> messages = List.of(message1, message2);
        Mockito.doReturn(messages).when(messageRepository).findByStoryId(storyId);
        List<MessageResponseTo> expected = messages
                .stream()
                .map(messageMapper::toMessageResponseTo)
                .toList();
        List<MessageResponseTo> actual = messageService.findByStoryId(storyId);
        assertEquals(expected, actual);
    }

    @Test
    void save() {
        long id = 1L;
        Message message = Message.builder()
                .id(id)
                .content("some content")
                .build();
        Mockito.doReturn(message).when(messageRepository).save(message);
        MessageResponseTo expected = messageMapper.toMessageResponseTo(message);
        MessageRequestTo messageRequestTo = messageMapper.toMessageRequestTo(message);
        MessageResponseTo actual = messageService.save(messageRequestTo);
        assertEquals(expected, actual);
    }

    @Test
    void whenUpdate_thenObjectsEquals() {
        long id = 1L;
        Message message = Message.builder()
                .id(id)
                .content("some content")
                .build();
        Message updatedMessage = Message.builder()
                .id(id)
                .content("new content")
                .build();
        Mockito.doReturn(Optional.of(message)).when(messageRepository).findById(id);
        Mockito.doReturn(updatedMessage).when(messageRepository).save(updatedMessage);
        MessageRequestTo messageRequestTo = messageMapper.toMessageRequestTo(updatedMessage);
        MessageResponseTo expected = messageMapper.toMessageResponseTo(updatedMessage);
        MessageResponseTo actual = messageService.update(messageRequestTo);
        assertEquals(expected, actual);
    }

    @Test
    void whenUpdate_thenFieldsUpdated() {
        Story story = Story.builder()
                .id(1L)
                .content("content")
                .title("title").build();
        long id = 1L;
        Message message = Message.builder()
                .id(id)
                .content("some content")
                .story(story)
                .build();
        Message updatedMessage = Message.builder()
                .id(id)
                .content("new content")
                .story(story)
                .build();
        Mockito.doReturn(Optional.of(message)).when(messageRepository).findById(id);
        Mockito.doReturn(Optional.of(story)).when(storyRepository).findById(story.getId());
        Mockito.doReturn(updatedMessage).when(messageRepository).save(updatedMessage);
        MessageRequestTo messageRequestTo = messageMapper.toMessageRequestTo(updatedMessage);
        MessageResponseTo expected = messageMapper.toMessageResponseTo(updatedMessage);
        MessageResponseTo actual = messageService.update(messageRequestTo);
        assertEquals(expected.getContent(), actual.getContent());
        assertEquals(expected.getStoryId(), actual.getStoryId());
    }

    @Test
    void deleteById() {
        long id = 1L;
        Message message = Message.builder()
                .id(id)
                .content("some content")
                .build();
        Mockito.doReturn(Optional.of(message)).when(messageRepository).findById(id);
        messageService.deleteById(id);
        Mockito.verify(messageRepository, Mockito.times(1)).findById(id);
        Mockito.verify(messageRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void whenDeleteByNonExistingId_thenThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> messageService.deleteById(-1L));
    }
}