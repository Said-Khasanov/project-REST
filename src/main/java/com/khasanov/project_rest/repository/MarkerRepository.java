package com.khasanov.project_rest.repository;

import com.khasanov.project_rest.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarkerRepository extends JpaRepository<Marker, Long> {
    @Query("SELECT s.markers FROM Story s where s.id = :storyId")
    List<Marker> findByStoryId(@Param("storyId") Long storyId);
}
