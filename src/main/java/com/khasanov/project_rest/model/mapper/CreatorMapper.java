package com.khasanov.project_rest.model.mapper;

import com.khasanov.project_rest.model.dto.request.CreatorRequestTo;
import com.khasanov.project_rest.model.dto.response.CreatorResponseTo;
import com.khasanov.project_rest.model.entity.Creator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreatorMapper {
    Creator toEntity(CreatorRequestTo creatorRequestTo);

    Creator toEntity(CreatorResponseTo creatorRequestTo);

    CreatorRequestTo toCreatorRequestTo(Creator creator);

    CreatorResponseTo toCreatorResponseTo(Creator creator);
}
