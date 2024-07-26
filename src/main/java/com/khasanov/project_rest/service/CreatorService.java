package com.khasanov.project_rest.service;

import com.khasanov.project_rest.model.dto.request.CreatorRequestTo;
import com.khasanov.project_rest.model.dto.response.CreatorResponseTo;
import com.khasanov.project_rest.model.entity.Creator;
import com.khasanov.project_rest.model.mapper.CreatorMapper;
import com.khasanov.project_rest.repository.CreatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNullElse;

@RequiredArgsConstructor
@Service
public class CreatorService {
    private final CreatorRepository creatorRepository;
    private final CreatorMapper creatorMapper;

    public List<CreatorResponseTo> findAll() {
        return creatorRepository.findAll().stream()
                .map(creatorMapper::toCreatorResponseTo)
                .toList();
    }

    public CreatorResponseTo findById(Long id) {
        Creator creator = creatorRepository.findById(id).orElseThrow();
        return creatorMapper.toCreatorResponseTo(creator);
    }

    public CreatorResponseTo createCreator(CreatorRequestTo creatorRequestTo) {
        Creator creator = creatorMapper.toEntity(creatorRequestTo);
        Creator creatorInDb = creatorRepository.save(creator);
        return creatorMapper.toCreatorResponseTo(creatorInDb);
    }

    public CreatorResponseTo updateCreator(CreatorRequestTo creatorRequestTo) {
        CreatorResponseTo creatorResponseTo = findById(creatorRequestTo.getId());
        Creator creator = creatorMapper.toEntity(creatorResponseTo);
        creator.setLogin(requireNonNullElse(creatorRequestTo.getLogin(), creator.getLogin()));
        creator.setFirstname(requireNonNullElse(creatorRequestTo.getFirstname(), creator.getFirstname()));
        creator.setLastname(requireNonNullElse(creatorRequestTo.getLastname(), creator.getLastname()));
        creator.setPassword(requireNonNullElse(creatorRequestTo.getPassword(), creator.getPassword()));
        Creator updatedCreator = creatorRepository.save(creator);
        return creatorMapper.toCreatorResponseTo(updatedCreator);
    }

    public void deleteCreator(Long id) {
        creatorRepository.deleteById(id);
    }
}
