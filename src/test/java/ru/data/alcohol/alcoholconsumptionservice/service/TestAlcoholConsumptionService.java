package ru.data.alcohol.alcoholconsumptionservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.data.alcohol.alcoholconsumptionservice.model.AlcoholConsumptionRecord;
import ru.data.alcohol.alcoholconsumptionservice.repository.CommonRepository;

import java.util.List;

// Тестовая реализация сервиса для проверки бизнес-логики в Л8
public class TestAlcoholConsumptionService implements AlcoholConsumptionServiceInterface {

    private final CommonRepository<AlcoholConsumptionRecord, Long> repository;

    public TestAlcoholConsumptionService(CommonRepository<AlcoholConsumptionRecord, Long> repository) {
        this.repository = repository;
    }

    @Override
    public List<AlcoholConsumptionRecord> getAll() {
        return repository.findAll();
    }

    @Override
    public AlcoholConsumptionRecord getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Запись не найдена"));
    }

    @Override
    public AlcoholConsumptionRecord create(AlcoholConsumptionRecord record) {
        record.setId(null);
        return repository.save(record);
    }

    @Override
    public AlcoholConsumptionRecord update(Long id, AlcoholConsumptionRecord record) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Запись не найдена");
        }

        record.setId(id);
        return repository.save(record);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Запись не найдена");
        }

        repository.deleteById(id);
    }
}
