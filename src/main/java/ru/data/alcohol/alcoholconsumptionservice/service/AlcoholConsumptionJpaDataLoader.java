package ru.data.alcohol.alcoholconsumptionservice.service;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.data.alcohol.alcoholconsumptionservice.model.AlcoholConsumptionRecord;
import ru.data.alcohol.alcoholconsumptionservice.repository.AlcoholConsumptionCsvRepository;
import ru.data.alcohol.alcoholconsumptionservice.repository.AlcoholConsumptionJpaRepository;

@Component
@Profile("jpa")
public class AlcoholConsumptionJpaDataLoader {

    private final AlcoholConsumptionCsvRepository csvRepository;
    private final AlcoholConsumptionJpaRepository jpaRepository;

    public AlcoholConsumptionJpaDataLoader(AlcoholConsumptionCsvRepository csvRepository,
                                           AlcoholConsumptionJpaRepository jpaRepository) {
        this.csvRepository = csvRepository;
        this.jpaRepository = jpaRepository;
    }

    @PostConstruct
    public void loadData() {
        if (jpaRepository.count() > 0) {
            return;
        }

        for (AlcoholConsumptionRecord record : csvRepository.findAll()) {
            record.setId(null);
            jpaRepository.save(record);
        }
    }
}