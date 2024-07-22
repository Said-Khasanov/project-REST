package com.khasanov.project_rest.service;

import com.khasanov.project_rest.model.dto.request.CreatorRequestTo;
import com.khasanov.project_rest.model.dto.response.CreatorResponseTo;
import com.khasanov.project_rest.model.entity.Creator;
import com.khasanov.project_rest.model.mapper.CreatorMapper;
import com.khasanov.project_rest.repository.CreatorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreatorService {
    private final CreatorRepository creatorRepository;
    private final CreatorMapper creatorMapper;

    public CreatorResponseTo findById(Long id) {
        Creator creator = creatorRepository.findById(id).orElseThrow();
        return creatorMapper.toCreatorResponseTo(creator);
    }

    public CreatorResponseTo createCreator(CreatorRequestTo creatorRequestTo) {
        Creator creator = creatorMapper.toEntity(creatorRequestTo);
        Creator creatorInDb = creatorRepository.save(creator);
        return creatorMapper.toCreatorResponseTo(creatorInDb);
    }

    public void updateCreator(CreatorRequestTo creatorRequestTo) {
        Creator creator = creatorMapper.toEntity(creatorRequestTo);
        creatorRepository.save(creator);
    }

    public void deleteCreator(Long id) {
        creatorRepository.deleteById(id);
    }
}
