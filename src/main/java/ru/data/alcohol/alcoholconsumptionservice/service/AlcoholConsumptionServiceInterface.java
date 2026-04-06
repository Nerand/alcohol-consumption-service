package ru.data.alcohol.alcoholconsumptionservice.service;

import ru.data.alcohol.alcoholconsumptionservice.model.AlcoholConsumptionRecord;

import java.util.List;

public interface AlcoholConsumptionServiceInterface {
    List<AlcoholConsumptionRecord> getAll();
    AlcoholConsumptionRecord getById(Long id);
    AlcoholConsumptionRecord create(AlcoholConsumptionRecord record);
    AlcoholConsumptionRecord update(Long id, AlcoholConsumptionRecord record);
    void delete(Long id);
}