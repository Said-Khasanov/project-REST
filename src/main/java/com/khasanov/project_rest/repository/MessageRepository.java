package com.khasanov.project_rest.repository;

import com.khasanov.project_rest.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
