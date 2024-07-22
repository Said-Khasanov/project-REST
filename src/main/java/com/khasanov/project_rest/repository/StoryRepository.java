package com.khasanov.project_rest.repository;

import com.khasanov.project_rest.model.entity.Story;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends CrudRepository<Story, Long> {
}
