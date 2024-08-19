package com.khasanov.project_rest.service;

import com.khasanov.project_rest.dto.request.CreatorRequestTo;
import com.khasanov.project_rest.dto.response.CreatorResponseTo;
import com.khasanov.project_rest.entity.Creator;
import com.khasanov.project_rest.mapper.CreatorMapper;
import com.khasanov.project_rest.mapper.CreatorMapperImpl;
import com.khasanov.project_rest.repository.CreatorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreatorServiceTest {

    private CreatorService creatorService;
    private CreatorRepository creatorRepository;
    private static final CreatorMapper creatorMapper = new CreatorMapperImpl();

    @BeforeEach
    void setUp() {
        creatorRepository = Mockito.mock(CreatorRepository.class);
        creatorService = new CreatorService(creatorRepository, creatorMapper);
    }

    @Test
    void findAll() {
        Creator creator1 = Creator.builder()
                .id(1L)
                .login("someLogin1")
                .password("somePassword1")
                .firstname("someFirstname1")
                .lastname("someLastname1")
                .build();
        Creator creator2 = Creator.builder()
                .id(2L)
                .login("someLogin1")
                .password("somePassword2")
                .firstname("someFirstname2")
                .lastname("someLastname2")
                .build();
        List<Creator> creators = List.of(creator1, creator2);
        List<CreatorResponseTo> expected = creators
                .stream()
                .map(creatorMapper::toCreatorResponseTo)
                .toList();
        Mockito.doReturn(creators).when(creatorRepository).findAll();
        List<CreatorResponseTo> actual = creatorService.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findById() {
        long id = 1L;
        Creator creator = Creator.builder()
                .id(id)
                .login("someLogin1")
                .password("somePassword1")
                .firstname("someFirstname1")
                .lastname("someLastname1")
                .build();
        Mockito.doReturn(Optional.of(creator)).when(creatorRepository).findById(id);
        CreatorResponseTo expected = creatorMapper.toCreatorResponseTo(creator);
        CreatorResponseTo actual = creatorService.findById(id);
        assertEquals(expected, actual);
    }

    @Test
    void whenFindByNonExistingId_thenThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> creatorService.findById(-1L));
    }

    @Test
    void findByStoryId() {
        long creatorId = 1L;
        long storyId = 1L;
        Creator expected = Creator.builder()
                .id(creatorId)
                .login("someLogin")
                .password("somePassword")
                .firstname("someFirstname")
                .lastname("someLastname")
                .build();
        Mockito.doReturn(expected).when(creatorRepository).findByStoryId(creatorId);
        CreatorResponseTo creatorResponseTo = creatorService.findByStoryId(storyId);
        Creator actual = creatorMapper.toEntity(creatorResponseTo);
        assertEquals(expected, actual);
    }

    @Test
    void save() {
        long id = 1L;
        Creator creator = Creator.builder()
                .id(id)
                .login("someLogin1")
                .password("somePassword1")
                .firstname("someFirstname1")
                .lastname("someLastname1")
                .build();
        CreatorResponseTo expected = creatorMapper.toCreatorResponseTo(creator);
        CreatorRequestTo creatorRequestTo = creatorMapper.toCreatorRequestTo(creator);
        Mockito.doReturn(creator).when(creatorRepository).save(creator);
        CreatorResponseTo actual = creatorService.save(creatorRequestTo);
        assertEquals(expected, actual);
    }

    @Test
    void whenUpdate_thenObjectsEquals() {
        long id = 1L;
        Creator creator = Creator.builder()
                .id(id)
                .login("someLogin1")
                .password("somePassword1")
                .firstname("someFirstname1")
                .lastname("someLastname1")
                .build();
        Creator updatedCreator = Creator.builder()
                .id(id)
                .login("newLogin1")
                .password("newPassword1")
                .firstname("newFirstname1")
                .lastname("newLastname1")
                .build();
        Mockito.doReturn(Optional.of(creator)).when(creatorRepository).findById(id);
        Mockito.doReturn(updatedCreator).when(creatorRepository).save(updatedCreator);
        CreatorRequestTo creatorRequestTo = creatorMapper.toCreatorRequestTo(updatedCreator);
        CreatorResponseTo expected = creatorMapper.toCreatorResponseTo(updatedCreator);
        CreatorResponseTo actual = creatorService.update(creatorRequestTo);
        assertEquals(expected, actual);
    }

    @Test
    void whenUpdate_thenFieldsUpdated() {
        long id = 1L;
        Creator creator = Creator.builder()
                .id(id)
                .login("someLogin1")
                .password("somePassword1")
                .firstname("someFirstname1")
                .lastname("someLastname1")
                .build();
        Creator updatedCreator = Creator.builder()
                .id(id)
                .login("someLogin1")
                .password("someLogin1")
                .firstname("newFirstname1")
                .lastname("newLastname1")
                .build();
        Mockito.doReturn(Optional.of(creator)).when(creatorRepository).findById(id);
        Mockito.doReturn(updatedCreator).when(creatorRepository).save(updatedCreator);
        CreatorRequestTo creatorRequestTo = creatorMapper.toCreatorRequestTo(updatedCreator);
        CreatorResponseTo expected = creatorMapper.toCreatorResponseTo(updatedCreator);
        CreatorResponseTo actual = creatorService.update(creatorRequestTo);
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
    }

    @Test
    void deleteById() {
        long id = 1L;
        Creator creator = Creator.builder()
                .id(id)
                .login("someLogin1")
                .password("somePassword1")
                .firstname("someFirstname1")
                .lastname("someLastname1")
                .build();
        Mockito.doReturn(Optional.of(creator)).when(creatorRepository).findById(1L);
        creatorService.deleteById(id);
        Mockito.verify(creatorRepository, Mockito.times(1)).findById(id);
        Mockito.verify(creatorRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void whenDeleteByNonExistingId_thenThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> creatorService.deleteById(-1L));
    }
}