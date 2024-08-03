package com.khasanov.project_rest.mapper;

import com.khasanov.project_rest.dto.request.CreatorRequestTo;
import com.khasanov.project_rest.dto.response.CreatorResponseTo;
import com.khasanov.project_rest.entity.Creator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreatorMapper {
    Creator toEntity(CreatorRequestTo creatorRequestTo);

    Creator toEntity(CreatorResponseTo creatorResponseTo);

    CreatorResponseTo toCreatorResponseTo(Creator creator);
}
