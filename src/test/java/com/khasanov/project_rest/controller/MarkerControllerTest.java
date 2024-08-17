package com.khasanov.project_rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khasanov.project_rest.entity.Marker;
import lombok.RequiredArgsConstructor;
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
class MarkerControllerTest {
    @Value("${spring.application.api-v1-prefix}")
    private String apiPrefix;
    public static final String RESOURCE = "/markers";
    public static final String ID_PATTERN = "/{id}";
    public static final String SOME_MARKER = """
            {
                "id": "",
                "name": "someName"
            }""";

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

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
                .content(SOME_MARKER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        Marker marker = objectMapper.readValue(json, Marker.class);

        mockMvc.perform(get(apiPrefix + RESOURCE + ID_PATTERN, marker.getId()))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    void save() throws Exception {
        mockMvc.perform(post(apiPrefix + RESOURCE)
                        .content(SOME_MARKER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void update() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(apiPrefix + RESOURCE)
                .content(SOME_MARKER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        Marker marker = objectMapper.readValue(json, Marker.class);
        marker.setName("newName");
        String updated_marker = objectMapper.writeValueAsString(marker);

        mockMvc.perform(put(apiPrefix + RESOURCE)
                        .content(updated_marker)
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
                .content(SOME_MARKER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        Marker marker = objectMapper.readValue(json, Marker.class);
        marker.setName("x");
        String incorrect_marker = objectMapper.writeValueAsString(marker);

        mockMvc.perform(put(apiPrefix + RESOURCE)
                        .content(incorrect_marker)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(apiPrefix + RESOURCE)
                .content(SOME_MARKER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        Marker marker = objectMapper.readValue(json, Marker.class);

        mockMvc.perform(delete(apiPrefix + RESOURCE + ID_PATTERN, marker.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDeleteByNonExistingId_thenStatusIs4xx() throws Exception {
        mockMvc.perform(delete(apiPrefix + RESOURCE + ID_PATTERN, 0))
                .andExpect(status().is4xxClientError());
    }
}