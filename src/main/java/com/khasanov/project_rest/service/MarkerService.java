package com.khasanov.project_rest.service;

import com.khasanov.project_rest.dto.request.MarkerRequestTo;
import com.khasanov.project_rest.dto.response.MarkerResponseTo;
import com.khasanov.project_rest.entity.Marker;
import com.khasanov.project_rest.mapper.MarkerMapper;
import com.khasanov.project_rest.repository.MarkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNullElse;

@RequiredArgsConstructor
@Service
public class MarkerService {
    private final MarkerRepository markerRepository;
    private final MarkerMapper markerMapper;

    public List<MarkerResponseTo> findAll() {
        return markerRepository.findAll().stream()
                .map(markerMapper::toMarkerResponseTo)
                .toList();
    }

    public MarkerResponseTo findById(Long id) {
        Marker marker = markerRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return markerMapper.toMarkerResponseTo(marker);
    }

    public List<MarkerResponseTo> findByStoryId(Long storyId) {
        return markerRepository.findByStoryId(storyId)
                .stream()
                .map(markerMapper::toMarkerResponseTo)
                .toList();
    }

    public MarkerResponseTo save(MarkerRequestTo markerRequestTo) {
        Marker marker = markerMapper.toEntity(markerRequestTo);
        Marker markerInDb = markerRepository.save(marker);
        return markerMapper.toMarkerResponseTo(markerInDb);
    }

    public MarkerResponseTo update(MarkerRequestTo markerRequestTo) {
        MarkerResponseTo markerResponseTo = findById(markerRequestTo.getId());
        Marker marker = markerMapper.toEntity(markerResponseTo);
        marker.setName(requireNonNullElse(markerRequestTo.getName(), marker.getName()));
        Marker updatedMarker = markerRepository.save(marker);
        return markerMapper.toMarkerResponseTo(updatedMarker);
    }

    public void deleteById(Long id) {
        findById(id);
        markerRepository.deleteById(id);
    }
}
