package com.khasanov.project_rest.service;

import com.khasanov.project_rest.dto.request.MarkerRequestTo;
import com.khasanov.project_rest.dto.response.MarkerResponseTo;
import com.khasanov.project_rest.entity.Marker;
import com.khasanov.project_rest.mapper.MarkerMapper;
import com.khasanov.project_rest.mapper.MarkerMapperImpl;
import com.khasanov.project_rest.repository.MarkerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MarkerServiceTest {

    private MarkerService markerService;
    private MarkerRepository markerRepository;
    private static final MarkerMapper markerMapper = new MarkerMapperImpl();

    @BeforeEach
    void setUp() {
        markerRepository = Mockito.mock(MarkerRepository.class);
        markerService = new MarkerService(markerRepository, markerMapper);

    }

    @Test
    void findAll() {
        Marker marker1 = Marker.builder()
                .id(1L)
                .name("marker1")
                .build();
        Marker marker2 = Marker.builder()
                .id(2L)
                .name("marker2")
                .build();
        List<Marker> markers = List.of(marker1, marker2);
        Mockito.doReturn(markers).when(markerRepository).findAll();
        List<MarkerResponseTo> expected = markers.stream().map(markerMapper::toMarkerResponseTo).toList();
        List<MarkerResponseTo> actual = markerService.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findById() {
        long id = 1L;
        Marker marker = Marker.builder()
                .id(id)
                .name("marker1")
                .build();
        Mockito.doReturn(Optional.of(marker)).when(markerRepository).findById(id);
        MarkerResponseTo expected = markerMapper.toMarkerResponseTo(marker);
        MarkerResponseTo actual = markerService.findById(id);
        assertEquals(expected, actual);
    }

    @Test
    void whenFindByNonExistingId_thenThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> markerService.findById(-1L));
    }

    @Test
    void findByStoryId() {
        Marker marker1 = Marker.builder()
                .id(1L)
                .name("someMarker1")
                .build();
        Marker marker2 = Marker.builder()
                .id(2L)
                .name("someMarker2")
                .build();
        List<Marker> markers = List.of(marker1, marker2);
        List<MarkerResponseTo> expected = markers.stream().map(markerMapper::toMarkerResponseTo).toList();
        Mockito.doReturn(markers).when(markerRepository).findByStoryId(1L);
        List<MarkerResponseTo> actual = markerService.findByStoryId(1L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void save() {
        long id = 1L;
        Marker marker = Marker.builder()
                .id(id)
                .name("marker1")
                .build();
        Mockito.doReturn(marker).when(markerRepository).save(marker);
        MarkerRequestTo markerRequestTo = markerMapper.toMarkerRequestTo(marker);
        MarkerResponseTo expected = markerMapper.toMarkerResponseTo(marker);
        MarkerResponseTo actual = markerService.save(markerRequestTo);
        assertEquals(expected, actual);
    }

    @Test
    void whenUpdate_thenObjectsEquals() {
        long id = 1L;
        Marker marker = Marker.builder()
                .id(id)
                .name("marker1")
                .build();
        Marker updatedMarker = Marker.builder()
                .id(id)
                .name("newMarker1")
                .build();
        Mockito.doReturn(Optional.of(marker)).when(markerRepository).findById(id);
        Mockito.doReturn(updatedMarker).when(markerRepository).save(updatedMarker);
        MarkerRequestTo markerRequestTo = markerMapper.toMarkerRequestTo(updatedMarker);
        MarkerResponseTo expected = markerMapper.toMarkerResponseTo(updatedMarker);
        MarkerResponseTo actual = markerService.update(markerRequestTo);
        assertEquals(expected, actual);
    }


    @Test
    void whenUpdate_thenFieldsUpdated() {
        long id = 1L;
        Marker marker = Marker.builder()
                .id(id)
                .name("marker1")
                .build();
        Marker updatedMarker = Marker.builder()
                .id(id)
                .name("newMarker1")
                .build();
        Mockito.doReturn(Optional.of(marker)).when(markerRepository).findById(id);
        Mockito.doReturn(updatedMarker).when(markerRepository).save(updatedMarker);
        MarkerRequestTo markerRequestTo = markerMapper.toMarkerRequestTo(updatedMarker);
        MarkerResponseTo expected = markerMapper.toMarkerResponseTo(updatedMarker);
        MarkerResponseTo actual = markerService.update(markerRequestTo);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void deleteById() {
        long id = 1L;
        Marker marker = Marker.builder()
                .id(id)
                .name("marker1")
                .build();
        Mockito.doReturn(Optional.of(marker)).when(markerRepository).findById(id);
        markerService.deleteById(id);
        Mockito.verify(markerRepository, Mockito.times(1)).findById(id);
        Mockito.verify(markerRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void whenDeleteByNonExistingId_thenThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> markerService.deleteById(-1L));
    }
}