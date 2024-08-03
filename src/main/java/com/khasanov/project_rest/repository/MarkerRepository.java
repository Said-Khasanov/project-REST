package com.khasanov.project_rest.repository;

import com.khasanov.project_rest.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkerRepository extends JpaRepository<Marker, Long> {
}
