package com.khasanov.project_rest.service;

import com.khasanov.project_rest.dto.request.StoryRequestTo;
import com.khasanov.project_rest.dto.response.StoryResponseTo;
import com.khasanov.project_rest.entity.Creator;
import com.khasanov.project_rest.entity.Story;
import com.khasanov.project_rest.mapper.StoryMapper;
import com.khasanov.project_rest.mapper.StoryMapperImpl;
import com.khasanov.project_rest.repository.CreatorRepository;
import com.khasanov.project_rest.repository.StoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StoryServiceTest {

    private StoryService storyService;
    private StoryRepository storyRepository;
    private CreatorRepository creatorRepository;
    private static final StoryMapper storyMapper = new StoryMapperImpl();

    @BeforeEach
    void setUp() {
        storyRepository = Mockito.mock(StoryRepository.class);
        creatorRepository = Mockito.mock(CreatorRepository.class);
        storyService = new StoryService(storyRepository, creatorRepository, storyMapper);
    }

    @Test
    void findAll() {
        Story story1 = Story.builder()
                .id(1L)
                .title("some title 1")
                .content("some content 1")
                .build();
        Story story2 = Story.builder()
                .id(2L)
                .title("some title 2")
                .content("some content 2")
                .build();
        List<Story> stories = List.of(story1, story2);
        Mockito.doReturn(stories).when(storyRepository).findAll();
        List<StoryResponseTo> expected = stories
                .stream()
                .map(storyMapper::toStoryResponseTo)
                .toList();
        List<StoryResponseTo> actual = storyService.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findById() {
        long id = 1L;
        Story story = Story.builder()
                .id(id)
                .title("some title 1")
                .content("some content 1")
                .build();
        Mockito.doReturn(Optional.of(story)).when(storyRepository).findById(id);
        StoryResponseTo expected = storyMapper.toStoryResponseTo(story);
        StoryResponseTo actual = storyService.findById(id);
        assertEquals(expected, actual);
    }

    @Test
    void whenFindByNonExistingId_thenThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> storyService.findById(-1L));
    }

    @Test
    void save() {
        Story story = Story.builder()
                .id(1L)
                .title("some title 1")
                .content("some content 1")
                .build();
        Mockito.doReturn(story).when(storyRepository).save(story);
        StoryResponseTo expected = storyMapper.toStoryResponseTo(story);
        StoryRequestTo storyRequestTo = storyMapper.toStoryRequestTo(story);
        StoryResponseTo actual = storyService.save(storyRequestTo);
        assertEquals(expected, actual);
    }

    @Test
    void whenUpdate_thenObjectsEquals() {
        long id = 1L;
        Story story = Story.builder()
                .id(id)
                .title("some title")
                .content("some content")
                .build();
        Story updatedStory = Story.builder()
                .id(id)
                .title("new title")
                .content("new content")
                .build();
        Mockito.doReturn(Optional.of(story)).when(storyRepository).findById(id);
        Mockito.doReturn(updatedStory).when(storyRepository).save(updatedStory);
        StoryResponseTo expected = storyMapper.toStoryResponseTo(updatedStory);
        StoryRequestTo storyRequestTo = storyMapper.toStoryRequestTo(updatedStory);
        StoryResponseTo actual = storyService.update(storyRequestTo);
        assertEquals(expected, actual);
    }

    @Test
    void whenUpdate_thenFieldsUpdated() {
        Creator creator = Creator.builder().id(1L).login("creator").build();
        long id = 1L;
        Story story = Story.builder()
                .id(id)
                .creator(creator)
                .title("some title")
                .content("some content")
                .modified(LocalDateTime.of(2024, 8, 18, 20, 30))
                .build();
        Story updatedStory = Story.builder()
                .id(id)
                .creator(creator)
                .title("new title")
                .content("new content")
                .modified(LocalDateTime.of(2024, 8, 19, 20, 30))
                .build();
        Mockito.doReturn(Optional.of(story)).when(storyRepository).findById(id);
        Mockito.doReturn(Optional.of(creator)).when(creatorRepository).findById(creator.getId());
        Mockito.doReturn(updatedStory).when(storyRepository).save(updatedStory);
        StoryResponseTo expected = storyMapper.toStoryResponseTo(updatedStory);
        StoryRequestTo storyRequestTo = storyMapper.toStoryRequestTo(updatedStory);
        StoryResponseTo actual = storyService.update(storyRequestTo);
        assertEquals(expected.getCreatorId(), actual.getCreatorId());
        assertEquals(expected.getContent(), actual.getContent());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getModified(), actual.getModified());
    }

    @Test
    void deleteById() {
        long id = 1L;
        Story story = Story.builder()
                .id(id)
                .title("some title")
                .content("some content")
                .build();
        Mockito.doReturn(Optional.of(story)).when(storyRepository).findById(id);
        storyService.deleteById(id);
        Mockito.verify(storyRepository, Mockito.times(1)).findById(id);
        Mockito.verify(storyRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void whenDeleteByNonExistingId_thenThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> storyService.deleteById(-1L));
    }
}