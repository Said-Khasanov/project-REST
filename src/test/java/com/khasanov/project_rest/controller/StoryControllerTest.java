package com.khasanov.project_rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khasanov.project_rest.entity.Creator;
import com.khasanov.project_rest.entity.Story;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@ActiveProfiles("test")
@Transactional
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
class StoryControllerTest {
    @Value("${spring.application.api-v1-prefix}")
    private String apiPrefix;
    public static final String RESOURCE = "/storys";
    public static final String ID_PATTERN = "/{id}";
    public static final String TEMPLATE_STORY = """
            {
                "title": "someTitle",
                "content": "someContent",
                "creatorId": %d
            }
            """;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private String someStory;

    @BeforeEach
    void setUp() throws Exception {
        MvcResult creatorMvcResult = mockMvc.perform(post(apiPrefix + "/creators")
                        .content("""
                                {
                                    "login": "someLogin",
                                    "password": "somePassword",
                                    "firstname": "someFirstname",
                                    "lastname": "someLastname"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        String creatorJson = creatorMvcResult.getResponse().getContentAsString();
        Creator creator = objectMapper.readValue(creatorJson, Creator.class);
        someStory = TEMPLATE_STORY.formatted(creator.getId());
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get(apiPrefix + RESOURCE))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    void findById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(apiPrefix + RESOURCE)
                .content(someStory)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        Story story = objectMapper.readValue(json, Story.class);

        mockMvc.perform(get(apiPrefix + RESOURCE + ID_PATTERN, story.getId()))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    void save() throws Exception {
        mockMvc.perform(post(apiPrefix + RESOURCE)
                        .content(someStory)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void update() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(apiPrefix + RESOURCE)
                .content(someStory)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        Story story = objectMapper.readValue(json, Story.class);
        story.setContent("newContent");
        story.setTitle("newTitle");
        String updated_story = objectMapper.writeValueAsString(story);

        mockMvc.perform(put(apiPrefix + RESOURCE)
                        .content(updated_story)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    void whenIncorrectUpdate_thenStatusIs4xx() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(apiPrefix + RESOURCE)
                .content(someStory)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        Story story = objectMapper.readValue(json, Story.class);
        story.setContent("x");
        story.setTitle("x");
        String incorrect_story = objectMapper.writeValueAsString(story);

        mockMvc.perform(put(apiPrefix + RESOURCE)
                        .content(incorrect_story)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(apiPrefix + RESOURCE)
                .content(someStory)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        Story story = objectMapper.readValue(json, Story.class);

        mockMvc.perform(delete(apiPrefix + RESOURCE + ID_PATTERN, story.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDeleteByNonExistingId_thenStatusIs4xx() throws Exception {
        mockMvc.perform(delete(apiPrefix + RESOURCE + ID_PATTERN, 0))
                .andExpect(status().is4xxClientError());
    }

}