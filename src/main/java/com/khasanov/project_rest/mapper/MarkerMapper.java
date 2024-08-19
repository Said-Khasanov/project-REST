package com.khasanov.project_rest.mapper;

import com.khasanov.project_rest.dto.request.MarkerRequestTo;
import com.khasanov.project_rest.dto.response.MarkerResponseTo;
import com.khasanov.project_rest.entity.Marker;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MarkerMapper {
    Marker toEntity(MarkerRequestTo markerRequestTo);

    Marker toEntity(MarkerResponseTo messageResponseTo);

    MarkerResponseTo toMarkerResponseTo(Marker marker);

    MarkerRequestTo toMarkerRequestTo(Marker marker);
}
