package com.chemcool.school.tasks.service;

import com.chemcool.school.tasks.domain.ChemistryEquationTask;
import com.chemcool.school.tasks.storage.ChemistryEquationTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChemistryEquationTaskServiceImpl implements ChemistryTaskService<ChemistryEquationTask> {

    private final ChemistryEquationTaskRepository repository;

    @Override
    public String add(ChemistryEquationTask chemistryEquationTask) {
        chemistryEquationTask.setId(UUID.randomUUID().toString());
        repository.save(chemistryEquationTask);
        return chemistryEquationTask.getId();
    }

    @Override
    public Optional<ChemistryEquationTask> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<ChemistryEquationTask> getAll() {
        return repository.findAll();
    }

    @Override
    public List<ChemistryEquationTask> getAllByChapterId(String id) {
        return repository.getAllByChapterId(id);
    }

    @Override
    public void update(ChemistryEquationTask chemistryEquationTask) {
        repository.save(chemistryEquationTask);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
