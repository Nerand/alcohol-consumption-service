package ru.data.alcohol.alcoholconsumptionservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.data.alcohol.alcoholconsumptionservice.model.AlcoholConsumptionRecord;

@Repository
public interface AlcoholConsumptionJpaRepository extends CrudRepository<AlcoholConsumptionRecord, Long> {
}