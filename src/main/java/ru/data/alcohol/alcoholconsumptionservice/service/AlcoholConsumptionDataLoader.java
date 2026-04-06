package ru.data.alcohol.alcoholconsumptionservice.service;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.data.alcohol.alcoholconsumptionservice.model.AlcoholConsumptionRecord;
import ru.data.alcohol.alcoholconsumptionservice.repository.AlcoholConsumptionCsvRepository;
import ru.data.alcohol.alcoholconsumptionservice.repository.AlcoholConsumptionJdbcRepository;

import java.util.List;

@Component
@Profile("jdbc")
public class AlcoholConsumptionDataLoader {

    private final AlcoholConsumptionCsvRepository csvRepository;
    private final AlcoholConsumptionJdbcRepository jdbcRepository;

    public AlcoholConsumptionDataLoader(AlcoholConsumptionCsvRepository csvRepository,
                                        AlcoholConsumptionJdbcRepository jdbcRepository) {
        this.csvRepository = csvRepository;
        this.jdbcRepository = jdbcRepository;
    }

    @PostConstruct
    public void loadDataToDatabase() {
        if (jdbcRepository.count() > 0) {
            return;
        }

        List<AlcoholConsumptionRecord> records = csvRepository.findAll();
        for (AlcoholConsumptionRecord record : records) {
            jdbcRepository.save(record);
        }
    }
}