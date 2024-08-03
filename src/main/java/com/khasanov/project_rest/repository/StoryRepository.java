package com.khasanov.project_rest.repository;

import com.khasanov.project_rest.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {
}
