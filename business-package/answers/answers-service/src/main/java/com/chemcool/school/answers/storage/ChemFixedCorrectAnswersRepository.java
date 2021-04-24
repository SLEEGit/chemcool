package com.chemcool.school.answers.storage;

import com.chemcool.school.answers.domain.ChemFixedCorrectAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChemFixedCorrectAnswersRepository extends JpaRepository<ChemFixedCorrectAnswers, String> {
}