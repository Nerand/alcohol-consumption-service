package ru.data.alcohol.alcoholconsumptionservice.service;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.data.alcohol.alcoholconsumptionservice.model.AlcoholConsumptionRecord;
import ru.data.alcohol.alcoholconsumptionservice.repository.AlcoholConsumptionJdbcRepository;

import java.util.List;

@Service
@Profile("jdbc")
public class AlcoholConsumptionJdbcService implements AlcoholConsumptionServiceInterface {

    private final AlcoholConsumptionJdbcRepository repository;
    private long nextId = 100000;

    public AlcoholConsumptionJdbcService(AlcoholConsumptionJdbcRepository repository) {
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
        record.setId(++nextId);
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