package com.chemcool.school.answerstask.service;

import com.chemcool.school.answerstask.domain.ChemEquationСorrectAnswers;
import com.chemcool.school.answerstask.domain.ChemFixedCorrectAnswers;
import com.chemcool.school.answerstask.storage.ChemFixedCorrectAnswersRepository;
import com.chemcool.school.answerstask.tasks.chemfixedanswer.domain.ChemFixedAnswerTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChemFixedCorrectAnswersService {
    private final ChemFixedCorrectAnswersRepository repository;

    public void saveCorrectAnswers(ChemFixedAnswerTask task) {
        repository.save(ChemFixedCorrectAnswers.createChemFixedCorrectAnswers(task));
    }

    public String getCorrectAnswerByIdTask(String taskId) {
        ChemFixedCorrectAnswers сorrectAnswers = repository.findById(taskId).orElseThrow(()-> new IllegalArgumentException("Нет задачи с таким id"));
        return сorrectAnswers.getCorrectAnswer();
    }
}
