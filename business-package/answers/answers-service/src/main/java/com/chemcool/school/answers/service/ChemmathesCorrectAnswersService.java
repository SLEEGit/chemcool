package com.chemcool.school.answers.service;

import com.chemcool.school.answers.domain.ChemmathesCorrectAnswers;
import com.chemcool.school.answers.storage.ChemmathesCorrectAnswersRepository;
import com.chemcool.school.answers.domain.matches.ChemistryMatchingTask;
import com.chemcool.school.answers.domain.matches.CoupleForMatching;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChemmathesCorrectAnswersService {
    private final ChemmathesCorrectAnswersRepository repository;

    public void saveCorrectAnswers(ChemistryMatchingTask task) {
        repository.save(ChemmathesCorrectAnswers.createChemmathesCorrectAnswers(task));
    }

    public List<CoupleForMatching> getCorrectCouplesByIdTask(String taskId) {
        ChemmathesCorrectAnswers correctAnswers = repository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Нет задачи с таким id"));
        return correctAnswers.getCorrectCoupleForMatchingList();
    }
}
