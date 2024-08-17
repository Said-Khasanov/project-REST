package com.khasanov.project_rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khasanov.project_rest.entity.Creator;
import com.khasanov.project_rest.entity.Message;
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
class MessageControllerTest {
    @Value("${spring.application.api-v1-prefix}")
    private String apiPrefix;
    public static final String RESOURCE = "/messages";
    public static final String ID_PATTERN = "/{id}";
    public static final String TEMPLATE_MESSAGE = """
            {
                "id": "",
                "storyId": %d,
                "content": "someContent"
            }""";

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private String someMessage;

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
        String storyJson = """
                {
                    "title": "someTitle",
                    "content": "someContent",
                    "creatorId": %d
                }
                """.formatted(creator.getId());
        MvcResult storyMvcResult = mockMvc.perform(post(apiPrefix + "/storys")
                        .content(storyJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        storyJson = storyMvcResult.getResponse().getContentAsString();
        Story story = objectMapper.readValue(storyJson, Story.class);
        someMessage = TEMPLATE_MESSAGE.formatted(story.getId());
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
                .content(someMessage)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        Message message = objectMapper.readValue(json, Message.class);

        mockMvc.perform(get(apiPrefix + RESOURCE + ID_PATTERN, message.getId()))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    void save() throws Exception {
        mockMvc.perform(post(apiPrefix + RESOURCE)
                        .content(someMessage)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void update() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(apiPrefix + RESOURCE)
                .content(someMessage)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        Message message = objectMapper.readValue(json, Message.class);
        message.setContent("newContent");
        String updated_message = objectMapper.writeValueAsString(message);

        mockMvc.perform(put(apiPrefix + RESOURCE)
                        .content(updated_message)
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
                .content(someMessage)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        Message message = objectMapper.readValue(json, Message.class);
        message.setContent("x");
        String incorrect_message = objectMapper.writeValueAsString(message);

        mockMvc.perform(put(apiPrefix + RESOURCE)
                        .content(incorrect_message)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(apiPrefix + RESOURCE)
                .content(someMessage)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        Message message = objectMapper.readValue(json, Message.class);

        mockMvc.perform(delete(apiPrefix + RESOURCE + ID_PATTERN, message.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDeleteByNonExistingId_thenStatusIs4xx() throws Exception {
        mockMvc.perform(delete(apiPrefix + RESOURCE + ID_PATTERN, 0))
                .andExpect(status().is4xxClientError());
    }

}