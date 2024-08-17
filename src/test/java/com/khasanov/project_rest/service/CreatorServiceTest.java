package com.khasanov.project_rest.service;

import com.khasanov.project_rest.dto.response.CreatorResponseTo;
import com.khasanov.project_rest.entity.Creator;
import com.khasanov.project_rest.mapper.CreatorMapper;
import com.khasanov.project_rest.mapper.CreatorMapperImpl;
import com.khasanov.project_rest.repository.CreatorRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
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
    void findByStoryId() {
        long id = 1L;
        Creator expected = Creator.builder()
                .id(id)
                .login("someLogin")
                .password("somePassword")
                .firstname("someFirstname")
                .lastname("someLastname")
                .build();

        Mockito.doReturn(expected).when(creatorRepository).findByStoryId(id);
        CreatorResponseTo creatorResponseTo = creatorService.findByStoryId(id);
        Creator actual = creatorMapper.toEntity(creatorResponseTo);
        assertEquals(expected, actual);
    }
}