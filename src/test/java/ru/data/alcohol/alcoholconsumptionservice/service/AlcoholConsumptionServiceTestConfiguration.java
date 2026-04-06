package ru.data.alcohol.alcoholconsumptionservice.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.data.alcohol.alcoholconsumptionservice.model.AlcoholConsumptionRecord;
import ru.data.alcohol.alcoholconsumptionservice.repository.CommonRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// Тестовая конфигурация для Л8
// Вместо реальной БД и CSV используем простое in-memory хранилище
@TestConfiguration
public class AlcoholConsumptionServiceTestConfiguration {

    @Bean
    public CommonRepository<AlcoholConsumptionRecord, Long> testRepository() {
        return new InMemoryTestRepository();
    }

    @Bean
    public TestAlcoholConsumptionService testAlcoholConsumptionService(CommonRepository<AlcoholConsumptionRecord, Long> repository) {
        return new TestAlcoholConsumptionService(repository);
    }

    static class InMemoryTestRepository implements CommonRepository<AlcoholConsumptionRecord, Long> {
        private final Map<Long, AlcoholConsumptionRecord> storage = new LinkedHashMap<>();
        private long nextId = 1L;

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
                entity.setId(nextId++);
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
}
