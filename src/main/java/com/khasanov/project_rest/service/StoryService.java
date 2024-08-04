package com.khasanov.project_rest.service;

import com.khasanov.project_rest.dto.request.StoryRequestTo;
import com.khasanov.project_rest.dto.response.StoryResponseTo;
import com.khasanov.project_rest.entity.Creator;
import com.khasanov.project_rest.entity.Story;
import com.khasanov.project_rest.mapper.StoryMapper;
import com.khasanov.project_rest.repository.CreatorRepository;
import com.khasanov.project_rest.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        Story story = storyRepository.findById(id).orElseThrow(NoSuchElementException::new);
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
        Long creatorId = storyRequestTo.getCreatorId();
        if (creatorId != null) {
            Creator creator = creatorRepository.findById(creatorId).orElseThrow(NoSuchElementException::new);
            story.setCreator(creator);
        }
        Story updatedStory = storyRepository.save(story);
        return storyMapper.toStoryResponseTo(updatedStory);
    }

    public void deleteById(Long id) {
        findById(id);
        storyRepository.deleteById(id);
    }
}
