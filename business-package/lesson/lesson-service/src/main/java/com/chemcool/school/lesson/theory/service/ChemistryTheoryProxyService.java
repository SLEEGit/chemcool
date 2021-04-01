package com.chemcool.school.lesson.theory.service;

import com.chemcool.school.lesson.theory.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChemistryTheoryProxyService {
    private final ChemTheoryEventNotificationService notificationService;
    private final ChemistryTheoryPageService pageService;

    public String add(ChemTheoryExample example) {
        ChemTheory theory = ChemTheoryFactory.createTheory(example);
        notificationService.send(
                ChemTheoryEventFactory.createEvent(theory, ChemTheoryEventType.CREATED)
        );
        return theory.getTheoryId();
    }

    public void delete(ChemTheory theory){
        pageService.delete(theory);
    }

    public void update(ChemTheory theory){
        notificationService.send(
                ChemTheoryEventFactory.createEvent(theory, ChemTheoryEventType.UPDATED)
        );
    }

    public List<ChemTheory> getAll() {return pageService.getAll();}

    public List<ChemTheory> getAllByChapterId(int chapterId){
                return pageService.getAllByChapterId(chapterId);
    }

    public List<ChemTheory> getAllByReferenceId(int referenceId){
                return pageService.getAllByReferenceId(referenceId);
    }

    public ChemTheory getById(String theoryId) {
        //  TODO проверить на то, что lessonId не пустой.
        if (theoryId == null || theoryId.isEmpty()) {
            throw new RuntimeException("theoryId параметр пустой, проверьте конфигурацию.");
        }
        return pageService.getAllByTheoryId(theoryId);
    }
}

