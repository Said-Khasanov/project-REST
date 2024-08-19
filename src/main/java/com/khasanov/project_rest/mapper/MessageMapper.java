package com.khasanov.project_rest.mapper;

import com.khasanov.project_rest.dto.request.MessageRequestTo;
import com.khasanov.project_rest.dto.response.MessageResponseTo;
import com.khasanov.project_rest.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {
    @Mapping(target = "story.id", source = "storyId")
    Message toEntity(MessageRequestTo messageRequestTo);

    @Mapping(target = "story.id", source = "storyId")
    Message toEntity(MessageResponseTo messageResponseTo);

    @Mapping(target = "storyId", source = "story.id")
    MessageResponseTo toMessageResponseTo(Message message);

    @Mapping(target = "storyId", source = "story.id")
    MessageRequestTo toMessageRequestTo(Message message);
}
