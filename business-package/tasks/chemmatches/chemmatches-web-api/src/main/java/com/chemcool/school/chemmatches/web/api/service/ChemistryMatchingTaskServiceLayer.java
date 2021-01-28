package com.chemcool.school.chemmatches.web.api.service;

import com.chemcool.school.chemmatches.domain.ChemistryMatchingTask;
import com.chemcool.school.chemmatches.domain.ChemistryMatchingTaskExample;
import com.chemcool.school.chemmatches.service.ChemistryMatchingTaskService;
import com.chemcool.school.chemmatches.web.api.exception.ChemistryTaskEmptyException;
import com.chemcool.school.chemmatches.web.api.dto.ChemistryMatchingTaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChemistryMatchingTaskServiceLayer {

    private final ChemistryMatchingTaskService service;

    public ChemistryMatchingTaskDto getTaskDtoById(String id) {
        return service.getById(id).map(ChemistryMatchingTaskDto::new).orElse(null);
    }

    public List<ChemistryMatchingTaskDto> getAllTasks() {
        List<ChemistryMatchingTaskDto> list = new ArrayList<>();
        for (ChemistryMatchingTask task : service.getAll()) {
            list.add(new ChemistryMatchingTaskDto(task));
        }
        return list;
    }

    public List<ChemistryMatchingTaskDto> getAllTasksByChapterId(String chapterId) {
        List<ChemistryMatchingTaskDto> list = new ArrayList<>();
        for (ChemistryMatchingTask task : service.getAllByChapterId(chapterId)) {
            list.add(new ChemistryMatchingTaskDto(task));
        }
        return list;
    }

    public String add(ChemistryMatchingTaskDto dto) {
        validate(dto);
        return service.add(
                new ChemistryMatchingTaskExample(
                        dto.getChemistryMatchingTaskUuid(),
                        dto.getDescription(),
                        dto.getChapterId(),
                        dto.getTypeOfTask(),
                        dto.getCoupleForMatchingList()
                )
        );
    }

    public void update(ChemistryMatchingTaskDto dto) {
        validate(dto);
        service.update(
                new ChemistryMatchingTask(
                        dto.getChemistryMatchingTaskUuid(),
                        dto.getDescription(),
                        dto.getChapterId(),
                        dto.getTypeOfTask(),
                        dto.getCoupleForMatchingList()
                )
        );
    }

    public void deleteById(String id) {
        service.deleteById(id);
    }

    private void validate(ChemistryMatchingTaskDto dto) {
        if (dto.getDescription().isEmpty()){
            throw new ChemistryTaskEmptyException("Необходимые поля пустые, проверьте пожалуйста бланк заполнения задания.");
        }
    }
}
