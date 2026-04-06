package ru.data.alcohol.alcoholconsumptionservice.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import ru.data.alcohol.alcoholconsumptionservice.model.AlcoholConsumptionRecord;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AlcoholConsumptionCsvRepository implements CommonRepository<AlcoholConsumptionRecord, Long> {

    private final ConcurrentHashMap<Long, AlcoholConsumptionRecord> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @PostConstruct
    public void init() {
        loadFromCsv();
    }

    private void loadFromCsv() {
        ClassPathResource resource = new ClassPathResource("data/alcohol_consumption.csv");

        try (Reader reader = new InputStreamReader(resource.getInputStream(), Charset.forName("windows-1251"));
             CSVReader csvReader = new CSVReader(reader)) {

            csvReader.readNext();

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                if (line.length < 6) {
                    continue;
                }

                Long id = idGenerator.incrementAndGet();

                AlcoholConsumptionRecord record = new AlcoholConsumptionRecord(
                        id,
                        line[0].trim(),
                        parseInteger(line[1]),
                        line[2].trim(),
                        parseDouble(line[3]),
                        parseDouble(line[4]),
                        parseDouble(line[5])
                );

                storage.put(id, record);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Не удалось загрузить данные из CSV", e);
        }
    }

    private Integer parseInteger(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Integer.parseInt(value.trim());
    }

    private Double parseDouble(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Double.parseDouble(value.trim().replace(",", "."));
    }

    @Override
    public List<AlcoholConsumptionRecord> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<AlcoholConsumptionRecord> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public AlcoholConsumptionRecord save(AlcoholConsumptionRecord entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}