package com.khasanov.project_rest.service;

import com.khasanov.project_rest.model.dto.request.StoryRequestTo;
import com.khasanov.project_rest.model.dto.response.StoryResponseTo;
import com.khasanov.project_rest.model.entity.Creator;
import com.khasanov.project_rest.model.entity.Story;
import com.khasanov.project_rest.model.mapper.StoryMapper;
import com.khasanov.project_rest.repository.CreatorRepository;
import com.khasanov.project_rest.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNullElse;

@RequiredArgsConstructor
@Service
public class StoryService {
    private final StoryRepository storyRepository;
    private final CreatorRepository creatorRepository;
    private final StoryMapper storyMapper;

    public List<StoryResponseTo> findAll() {
        return storyRepository.findAll().stream()
                .map(storyMapper::toStoryResponseTo)
                .toList();
    }

    public StoryResponseTo findById(Long id) {
        Story story = storyRepository.findById(id).orElseThrow();
        return storyMapper.toStoryResponseTo(story);
    }

    public StoryResponseTo save(StoryRequestTo storyRequestTo) {
        Story story = storyMapper.toEntity(storyRequestTo);
        Story storyInDb = storyRepository.save(story);
        return storyMapper.toStoryResponseTo(storyInDb);
    }

    public StoryResponseTo update(StoryRequestTo storyRequestTo) {
        StoryResponseTo storyResponseTo = findById(storyRequestTo.getId());
        Story story = storyMapper.toEntity(storyResponseTo);
        story.setContent(requireNonNullElse(storyRequestTo.getContent(), story.getContent()));
        story.setTitle(requireNonNullElse(storyRequestTo.getTitle(), story.getTitle()));
        story.setCreated(requireNonNullElse(storyRequestTo.getCreated(), story.getCreated()));
        story.setModified(requireNonNullElse(storyRequestTo.getModified(), story.getModified()));
        Long creatorId = requireNonNullElse(storyRequestTo.getCreatorId(), story.getCreator().getId());
        Creator creator = creatorRepository.findById(creatorId).orElseThrow();
        story.setCreator(creator);
        Story updatedStory = storyRepository.save(story);
        return storyMapper.toStoryResponseTo(updatedStory);
    }

    public void deleteById(Long id) {
        storyRepository.deleteById(id);
    }
}
