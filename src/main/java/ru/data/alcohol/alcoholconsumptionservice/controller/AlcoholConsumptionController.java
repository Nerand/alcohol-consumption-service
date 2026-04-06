package ru.data.alcohol.alcoholconsumptionservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.data.alcohol.alcoholconsumptionservice.dto.AlcoholConsumptionDto;
import ru.data.alcohol.alcoholconsumptionservice.mapper.AlcoholConsumptionMapper;
import ru.data.alcohol.alcoholconsumptionservice.model.AlcoholConsumptionRecord;
import ru.data.alcohol.alcoholconsumptionservice.service.AlcoholConsumptionServiceInterface;

import java.util.List;
import java.util.stream.Collectors;

// REST API для работы с данными о потреблении алкоголя
@RestController
@RequestMapping("/api/alcohol-consumption")
public class AlcoholConsumptionController {

    private final AlcoholConsumptionServiceInterface service;

    public AlcoholConsumptionController(AlcoholConsumptionServiceInterface service) {
        this.service = service;
    }

    // Получить все записи
    @GetMapping
    public List<AlcoholConsumptionDto> getAll() {
        return service.getAll().stream()
                .map(AlcoholConsumptionMapper::toDto)
                .collect(Collectors.toList());
    }

    // Получить одну запись по идентификатору
    @GetMapping("/{id}")
    public AlcoholConsumptionDto getById(@PathVariable Long id) {
        AlcoholConsumptionRecord record = service.getById(id);
        return AlcoholConsumptionMapper.toDto(record);
    }

    // Создать новую запись
    @PostMapping
    public AlcoholConsumptionDto create(@RequestBody AlcoholConsumptionDto dto) {
        AlcoholConsumptionRecord entity = AlcoholConsumptionMapper.toEntity(dto);
        AlcoholConsumptionRecord saved = service.create(entity);
        return AlcoholConsumptionMapper.toDto(saved);
    }

    // Обновить существующую запись
    @PutMapping("/{id}")
    public AlcoholConsumptionDto update(@PathVariable Long id,
                                        @RequestBody AlcoholConsumptionDto dto) {
        AlcoholConsumptionRecord entity = AlcoholConsumptionMapper.toEntity(dto);
        AlcoholConsumptionRecord updated = service.update(id, entity);
        return AlcoholConsumptionMapper.toDto(updated);
    }

    // Удалить запись по идентификатору
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
