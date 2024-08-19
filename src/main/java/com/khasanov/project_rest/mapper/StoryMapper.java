package com.khasanov.project_rest.mapper;

import com.khasanov.project_rest.dto.request.StoryRequestTo;
import com.khasanov.project_rest.dto.response.StoryResponseTo;
import com.khasanov.project_rest.entity.Story;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StoryMapper {
    @Mapping(target = "creator.id", source = "creatorId")
    Story toEntity(StoryRequestTo storyRequestTo);

    @Mapping(target = "creator.id", source = "creatorId")
    Story toEntity(StoryResponseTo storyResponseTo);

    @Mapping(target = "creatorId", source = "creator.id")
    StoryResponseTo toStoryResponseTo(Story story);

    @Mapping(target = "creatorId", source = "creator.id")
    StoryRequestTo toStoryRequestTo(Story story);
}
