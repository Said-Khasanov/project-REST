package com.khasanov.project_rest.repository;

import com.khasanov.project_rest.model.entity.Creator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepository extends CrudRepository<Creator, Long> {
}
