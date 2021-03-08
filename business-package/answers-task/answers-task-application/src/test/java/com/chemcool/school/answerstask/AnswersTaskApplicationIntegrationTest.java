package com.chemcool.school.answerstask;

import com.chemcool.school.answerstask.app.AnswersTaskApplication;
import com.chemcool.school.answerstask.domain.ChemEquationСorrectAnswers;
import com.chemcool.school.answerstask.domain.ChemFixedCorrectAnswers;
import com.chemcool.school.answerstask.domain.ChemSingleSelectCorrectAnswers;
import com.chemcool.school.answerstask.domain.ChemmathesCorrectAnswers;
import com.chemcool.school.answerstask.storage.*;
import com.chemcool.school.answerstask.tasks.chemmatches.domain.CoupleForMatching;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Перед запуском тестов поднять необходимо поднять сервис аутентификации получить токен и вставить его в поле token
 */
@SpringBootTest(classes = AnswersTaskApplication.class)
@Testcontainers
@AutoConfigureMockMvc
//написать тест для проверки сохранения пользователя правильно ответившего на вопрос и проверки на повторный ответ
public class AnswersTaskApplicationIntegrationTest extends RunTestcontainerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ChemEquationСorrectAnswersRepository chemEquationСorrectAnswersRepository;
    @Autowired
    private ChemFixedCorrectAnswersRepository chemFixedCorrectAnswersRepository;
    @Autowired
    private ChemmathesCorrectAnswersRepository chemmathesCorrectAnswersRepository;
    @Autowired
    private ChemSingleSelectCorrectAnswerRepository chemSingleSelectCorrectAnswerRepository;
    @Autowired
    private UserAnswersCorrectRepository userAnswersCorrectRepository;

    private ChemEquationСorrectAnswers chemEquationСorrectAnswers =
            new ChemEquationСorrectAnswers("c4e04c9b-test-equitation", "correctAnswerEquitation");

    private ChemFixedCorrectAnswers chemFixedCorrectAnswers =
            new ChemFixedCorrectAnswers("c4e04c9b-test-fixed", "correctAnswerFixed");

    private ChemmathesCorrectAnswers chemmathesCorrectAnswers = new ChemmathesCorrectAnswers("c4e04c9b-test-matches",
            List.of(new CoupleForMatching(1L, "leftCouple1", "rightCouple1"),
                    new CoupleForMatching(2L, "leftCouple2", "rightCouple2")));

    private ChemSingleSelectCorrectAnswers chemSingleSelectCorrectAnswers =
            new ChemSingleSelectCorrectAnswers("c4e04c9b-test-select", "correctAnswerSelect");

    private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJMYXpvdnNraTE5OTFAZ21haWwuY29tIiwibmljayI6Im5lbHNvbjkxIiwicm9sZSI6IlJPTEVfVVNFUl9CQVNFIiwidXNlcklkIjoiZTZjY2MxMzUtNGRjYi00MTMwLTkxYmQtZjZkYTQ0OWFiYjczIiwiZXhwIjoxNjE1MjQ0MzI1fQ.RrFJZQ9FaUptMtYH8D0dXUG6rawvkMxfyBzgNMSDvJXaq-_n105xRKpaffpoIw1SvQ0ZjqbAdbwMpmbsUNRtlQ";

    @AfterEach
    void cleanDataBaseUserCorrect() {
        userAnswersCorrectRepository.deleteAll();
    }

    @Test
    @DisplayName("")
    void checkingCorrectAnswerTaskWithFixedAnswer() throws Exception {
        chemFixedCorrectAnswersRepository.save(chemFixedCorrectAnswers);
        mockMvc.perform(post("/v1.0")
                .param("taskId", "c4e04c9b-test-fixed")
                .param("taskType", "FIXED_ANSWER")
                .param("userAnswers", "correctAnswerFixed")
                .header("AuthorizationToken", token))
                .andExpect(content().string("true"));
    }

    @Test
    void checkingNoCorrectAnswerTaskWithFixedAnswer() throws Exception {
        chemFixedCorrectAnswersRepository.save(chemFixedCorrectAnswers);
        mockMvc.perform(post("/v1.0")
                .param("taskId", "c4e04c9b-test-fixed")
                .param("taskType", "FIXED_ANSWER")
                .param("userAnswers", "noCorrectAnswerFixed")
                .header("AuthorizationToken", token))
                .andExpect(content().string("false"));
    }

    @Test
    void checkingCorrectAnswerTaskWithSingleSelectAnswer() throws Exception {
        chemSingleSelectCorrectAnswerRepository.save(chemSingleSelectCorrectAnswers);
        mockMvc.perform(post("/v1.0")
                .param("taskId", "c4e04c9b-test-select")
                .param("taskType", "SINGLE_SELECT")
                .param("userAnswers", "correctAnswerSelect")
                .header("AuthorizationToken", token))
                .andExpect(content().string("true"));
    }

    @Test
    void checkingNoCorrectAnswerTaskWithSingleSelectAnswer() throws Exception {
        chemSingleSelectCorrectAnswerRepository.save(chemSingleSelectCorrectAnswers);
        mockMvc.perform(post("/v1.0")
                .param("taskId", "c4e04c9b-test-select")
                .param("taskType", "SINGLE_SELECT")
                .param("userAnswers", "noCorrectAnswerSelect")
                .header("AuthorizationToken", token))
                .andExpect(content().string("false"));
    }

    @Test
    void checkingCorrectAnswerTaskWithEquitationAnswer() throws Exception {
        chemEquationСorrectAnswersRepository.save(chemEquationСorrectAnswers);
        mockMvc.perform(post("/v1.0")
                .param("taskId", "c4e04c9b-test-equitation")
                .param("taskType", "EQUATION")
                .param("userAnswers", "correctAnswerEquitation")
                .header("AuthorizationToken", token))
                .andExpect(content().string("true"));
    }

    @Test
    void checkingNoCorrectAnswerTaskWithEquitationAnswer() throws Exception {
        chemEquationСorrectAnswersRepository.save(chemEquationСorrectAnswers);
        mockMvc.perform(post("/v1.0")
                .param("taskId", "c4e04c9b-test-equitation")
                .param("taskType", "EQUATION")
                .param("userAnswers", "NoCorrectAnswerEquitation")
                .header("AuthorizationToken", token))
                .andExpect(content().string("false"));
    }

    @Test
    void checkingCorrectAnswerTaskWithMatchesAnswer() throws Exception {
        chemmathesCorrectAnswersRepository.save(chemmathesCorrectAnswers);
        mockMvc.perform(post("/v1.0")
                .param("taskId", "c4e04c9b-test-matches")
                .param("taskType", "MATCHES")
                .header("AuthorizationToken", token)
                .content(objectMapper.writeValueAsString(List.of(new CoupleForMatching("leftCouple1", "rightCouple1"),
                        new CoupleForMatching("leftCouple2", "rightCouple2"))))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));
    }

    @Test
    void checkingCorrectReversedAnswerTaskWithMatchesAnswer() throws Exception {
        chemmathesCorrectAnswersRepository.save(chemmathesCorrectAnswers);
        mockMvc.perform(post("/v1.0")
                .param("taskId", "c4e04c9b-test-matches")
                .param("taskType", "MATCHES")
                .header("AuthorizationToken", token)
                .content(objectMapper.writeValueAsString(List.of(new CoupleForMatching("leftCouple2", "rightCouple2"),
                        new CoupleForMatching("leftCouple1", "rightCouple1"))))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));
    }

    @Test
    void checkingNoCorrectReversedAnswerTaskWithMatchesAnswer() throws Exception {
        chemmathesCorrectAnswersRepository.save(chemmathesCorrectAnswers);
        mockMvc.perform(post("/v1.0")
                .param("taskId", "c4e04c9b-test-matches")
                .param("taskType", "MATCHES")
                .header("AuthorizationToken", token)
                .content(objectMapper.writeValueAsString(List.of(new CoupleForMatching("leftCouple1", "rightCouple2"),
                        new CoupleForMatching("leftCouple2", "rightCouple1"))))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));
    }

    @Test
    void checkingNoCorrectAnswerTaskWithMatchesAnswer() throws Exception {
        chemmathesCorrectAnswersRepository.save(chemmathesCorrectAnswers);
        mockMvc.perform(post("/v1.0")
                .param("taskId", "c4e04c9b-test-matches")
                .param("taskType", "MATCHES")
                .header("AuthorizationToken", token)
                .content(objectMapper.writeValueAsString(List.of(new CoupleForMatching("leftCouple1", "rightCouple1"),
                        new CoupleForMatching("leftCouple2", "rightCouple22"))))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));
    }
}
