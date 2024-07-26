package com.khasanov.project_rest.model.mapper;

import com.khasanov.project_rest.model.dto.request.StoryRequestTo;
import com.khasanov.project_rest.model.dto.response.StoryResponseTo;
import com.khasanov.project_rest.model.entity.Story;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CreatorMapper.class})
public interface StoryMapper {
    Story toEntity(StoryRequestTo storyRequestTo);

    Story toEntity(StoryResponseTo storyResponseTo);

    @Mapping(target = "creatorId", source = "creator.id")
    StoryResponseTo toStoryResponseTo(Story story);
}
