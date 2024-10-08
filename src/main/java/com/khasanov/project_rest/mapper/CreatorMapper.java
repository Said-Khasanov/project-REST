package com.khasanov.project_rest.mapper;

import com.khasanov.project_rest.dto.request.CreatorRequestTo;
import com.khasanov.project_rest.dto.response.CreatorResponseTo;
import com.khasanov.project_rest.entity.Creator;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreatorMapper {
    Creator toEntity(CreatorRequestTo creatorRequestTo);

    Creator toEntity(CreatorResponseTo creatorResponseTo);

    CreatorResponseTo toCreatorResponseTo(Creator creator);

    CreatorRequestTo toCreatorRequestTo(Creator creator);
}
