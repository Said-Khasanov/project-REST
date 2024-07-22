package com.khasanov.project_rest.repository;

import com.khasanov.project_rest.model.entity.Marker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkerRepository extends CrudRepository<Marker, Long> {
}
