package com.chemcool.school.tasks.service;

import com.chemcool.school.tasks.domain.ChemistryFixedAnswerTask;
import com.chemcool.school.tasks.storage.ChemistryFixedAnswerTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChemistryFixedAnswerTaskServiceImpl implements ChemistryTaskService<ChemistryFixedAnswerTask> {

    @Autowired
    ChemistryFixedAnswerTaskRepository repository;

    @Override
    public String add(ChemistryFixedAnswerTask chemistryFixedAnswerTask) {
        chemistryFixedAnswerTask.setId(UUID.randomUUID().toString());
        repository.save(chemistryFixedAnswerTask);
        return chemistryFixedAnswerTask.getId();
    }

    @Override
    public Optional<ChemistryFixedAnswerTask> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<ChemistryFixedAnswerTask> getAll() {
        return repository.findAll();
    }

    @Override
    public List<ChemistryFixedAnswerTask> getAllByChapterId(String id) { // ??????
        return repository.findAllByChapterId(id);
    }

    @Override
    public void update(ChemistryFixedAnswerTask chemistryFixedAnswerTask) {
        repository.save(chemistryFixedAnswerTask);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}