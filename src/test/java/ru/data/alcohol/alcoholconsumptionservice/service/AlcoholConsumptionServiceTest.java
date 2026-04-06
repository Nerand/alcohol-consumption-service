package ru.data.alcohol.alcoholconsumptionservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.server.ResponseStatusException;
import ru.data.alcohol.alcoholconsumptionservice.model.AlcoholConsumptionRecord;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Тесты сервиса на основе TestConfiguration для Л8
@SpringJUnitConfig(AlcoholConsumptionServiceTestConfiguration.class)
class AlcoholConsumptionServiceTest {

    @Autowired
    private TestAlcoholConsumptionService service;

    @Test
    void createShouldSaveRecord() {
        AlcoholConsumptionRecord record = new AlcoholConsumptionRecord();
        record.setRegion("Test region");
        record.setYear(2024);
        record.setBeverageType("Beer");
        record.setConsumptionThousandsDecaliters(10.0);
        record.setConsumptionLitersPerCapita(1.2);
        record.setConsumptionPureAlcoholPerCapita(0.3);

        AlcoholConsumptionRecord saved = service.create(record);

        assertNotNull(saved.getId());
        assertEquals("Test region", saved.getRegion());
        assertEquals(2024, saved.getYear());
    }

    @Test
    void getAllShouldReturnCreatedRecords() {
        AlcoholConsumptionRecord first = new AlcoholConsumptionRecord();
        first.setRegion("Region 1");
        first.setYear(2020);
        first.setBeverageType("Wine");

        AlcoholConsumptionRecord second = new AlcoholConsumptionRecord();
        second.setRegion("Region 2");
        second.setYear(2021);
        second.setBeverageType("Vodka");

        service.create(first);
        service.create(second);

        List<AlcoholConsumptionRecord> result = service.getAll();

        assertTrue(result.size() >= 2);
    }

    @Test
    void getByIdShouldReturnExistingRecord() {
        AlcoholConsumptionRecord record = new AlcoholConsumptionRecord();
        record.setRegion("Search region");
        record.setYear(2022);
        record.setBeverageType("Liqueurs");

        AlcoholConsumptionRecord saved = service.create(record);
        AlcoholConsumptionRecord found = service.getById(saved.getId());

        assertEquals(saved.getId(), found.getId());
        assertEquals("Search region", found.getRegion());
    }

    @Test
    void getByIdShouldThrowWhenRecordNotFound() {
        assertThrows(ResponseStatusException.class, () -> service.getById(999L));
    }

    @Test
    void updateShouldChangeExistingRecord() {
        AlcoholConsumptionRecord record = new AlcoholConsumptionRecord();
        record.setRegion("Old region");
        record.setYear(2021);
        record.setBeverageType("Beer");

        AlcoholConsumptionRecord saved = service.create(record);

        AlcoholConsumptionRecord updatedData = new AlcoholConsumptionRecord();
        updatedData.setRegion("New region");
        updatedData.setYear(2025);
        updatedData.setBeverageType("Vodka");
        updatedData.setConsumptionThousandsDecaliters(20.0);
        updatedData.setConsumptionLitersPerCapita(2.5);
        updatedData.setConsumptionPureAlcoholPerCapita(0.8);

        AlcoholConsumptionRecord updated = service.update(saved.getId(), updatedData);

        assertEquals(saved.getId(), updated.getId());
        assertEquals("New region", updated.getRegion());
        assertEquals(2025, updated.getYear());
        assertEquals("Vodka", updated.getBeverageType());
    }

    @Test
    void deleteShouldRemoveRecord() {
        AlcoholConsumptionRecord record = new AlcoholConsumptionRecord();
        record.setRegion("Delete region");
        record.setYear(2023);
        record.setBeverageType("Wine");

        AlcoholConsumptionRecord saved = service.create(record);
        service.delete(saved.getId());

        assertThrows(ResponseStatusException.class, () -> service.getById(saved.getId()));
    }
}
