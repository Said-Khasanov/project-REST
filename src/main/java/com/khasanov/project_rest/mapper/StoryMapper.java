package com.khasanov.project_rest.mapper;

import com.khasanov.project_rest.dto.request.StoryRequestTo;
import com.khasanov.project_rest.dto.response.StoryResponseTo;
import com.khasanov.project_rest.entity.Story;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CreatorMapper.class})
public interface StoryMapper {
    Story toEntity(StoryRequestTo storyRequestTo);

    Story toEntity(StoryResponseTo storyResponseTo);

    @Mapping(target = "creatorId", source = "creator.id")
    StoryResponseTo toStoryResponseTo(Story story);
}
