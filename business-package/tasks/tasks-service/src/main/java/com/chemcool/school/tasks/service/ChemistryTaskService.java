package com.chemcool.school.tasks.service;

import java.util.List;
import java.util.Optional;

public interface ChemistryTaskService<T> {
    String add(T t);
    Optional<T> getById(String id);
    List<T> getAll();
    List<T> getAllByChapterId(String chapterId);
    void update(T t);
    void deleteById(String id);
}

