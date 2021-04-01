package com.chemcool.school.lesson.tasks.chemmatches.service;

import com.chemcool.school.lesson.app.LessonApplication;
import com.chemcool.school.lesson.tasks.chemfixedanswer.domain.ChemFixedAnswerTask;
import com.chemcool.school.lesson.tasks.chemmatches.domain.ChemistryMatchingTask;
import com.chemcool.school.lesson.tasks.chemmatches.domain.ChemistryMatchingTaskExample;
import com.chemcool.school.lesson.tasks.chemmatches.domain.CoupleForMatching;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LessonApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class ChemistryMatchingTaskProxyServiceUnitTest {

    @Autowired
    private ChemistryMatchingTaskProxyService proxyService;

    @MockBean
    private ChemistryMatchingTaskService service;

    private List<ChemistryMatchingTask> taskList = new ArrayList<>();

    private String id;
    private Integer i;

    @BeforeEach
    void setUp() {
        List<CoupleForMatching> coupleForMatching = new ArrayList<>();
        coupleForMatching.add(new CoupleForMatching(1L,"He", "Гелий"));
        coupleForMatching.add(new CoupleForMatching(2L,"H", "Водород"));
        coupleForMatching.add(new CoupleForMatching(3L,"O", "Кислород"));
        coupleForMatching.add(new CoupleForMatching(4L,"C", "Углерод"));

       taskList.add(ChemistryMatchingTask.createChemistryMatchingTask(new ChemistryMatchingTaskExample(
               "1",
               "description_task1",
               1,
               1,
               coupleForMatching
       )));
        taskList.add(ChemistryMatchingTask.createChemistryMatchingTask(new ChemistryMatchingTaskExample(
                "2",
                "description_task2",
                1,
                2,
                coupleForMatching
        )));
        taskList.add(ChemistryMatchingTask.createChemistryMatchingTask(new ChemistryMatchingTaskExample(
                "3",
                "description_task3",
                2,
                2,
                coupleForMatching
        )));

        i = 0; //Элемент листа, при значения !=0 тест getById должен падать
        id = taskList.get(0).getTaskId();
        assertThat(id).isNotNull();
        System.out.println("Создана задача с id: " + id);
    }

    @Test
    public void contextTest() throws Exception {
        assertThat(proxyService).isNotNull();
    }

    @Test
    void getById() {
        Mockito.when(service.getById(id)).thenReturn(Optional.ofNullable(taskList.get(i)));
        ChemistryMatchingTask task = proxyService.getById(id).orElse(null);
        System.out.println("*****************\n"+task + "\n*****************\n");
        assertThat(task).isNotNull();
        assertThat(task.getDescription()).isEqualTo("description_task1");
    }

    @Test
    void getByFakeId() {
        Mockito.when(service.getById(id)).thenReturn(Optional.ofNullable(taskList.get(0)));
        ChemistryMatchingTask task = proxyService.getById(id+"1").orElse(null);
        System.out.println("*****************\n"+task + "\n*****************\n");
        assertThat(task).isNull();
    }

    @Test
    void getAll() {
        Mockito.when(service.getAll()).thenReturn(taskList);
        List<ChemistryMatchingTask> taskExamples = proxyService.getAll();
        System.out.println("*****************\n"+taskExamples + "\n*****************\n");
        assertThat(taskExamples).isNotNull();
        assertThat(taskExamples).hasSize(3);
    }

    @Test
    void getAllByChapterId() {
        Mockito.when(service.getAllByChapterId(1)).thenReturn(taskList.subList(0, 2));
        List<ChemistryMatchingTask> taskExamples = proxyService.getAllByChapterId(1);
        System.out.println("*****************\n"+taskExamples + "\n*****************\n");
        assertThat(taskExamples).isNotNull();
        assertThat(taskExamples).hasSize(2);
    }
}