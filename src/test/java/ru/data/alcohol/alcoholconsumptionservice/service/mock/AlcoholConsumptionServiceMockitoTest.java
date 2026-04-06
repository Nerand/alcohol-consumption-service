package ru.data.alcohol.alcoholconsumptionservice.service.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.data.alcohol.alcoholconsumptionservice.model.AlcoholConsumptionRecord;
import ru.data.alcohol.alcoholconsumptionservice.repository.CommonRepository;
import ru.data.alcohol.alcoholconsumptionservice.service.TestAlcoholConsumptionService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Тесты сервиса с использованием Mockito (Л9)
// Репозиторий полностью подменяется мок-объектом
class AlcoholConsumptionServiceMockitoTest {

    private CommonRepository<AlcoholConsumptionRecord, Long> repository;
    private TestAlcoholConsumptionService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(CommonRepository.class);
        service = new TestAlcoholConsumptionService(repository);
    }

    @Test
    void getByIdShouldReturnRecord() {
        AlcoholConsumptionRecord record = new AlcoholConsumptionRecord();
        record.setId(1L);
        record.setRegion("Test");

        when(repository.findById(1L)).thenReturn(Optional.of(record));

        AlcoholConsumptionRecord result = service.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Test", result.getRegion());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getByIdShouldThrowWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.getById(1L));
    }

    @Test
    void createShouldCallSave() {
        AlcoholConsumptionRecord record = new AlcoholConsumptionRecord();
        record.setRegion("Create");

        when(repository.save(any())).thenAnswer(invocation -> {
            AlcoholConsumptionRecord r = invocation.getArgument(0);
            r.setId(100L);
            return r;
        });

        AlcoholConsumptionRecord result = service.create(record);

        assertNotNull(result.getId());
        verify(repository, times(1)).save(any());
    }

    @Test
    void updateShouldUpdateExistingRecord() {
        when(repository.existsById(1L)).thenReturn(true);

        AlcoholConsumptionRecord updated = new AlcoholConsumptionRecord();
        updated.setRegion("Updated");

        when(repository.save(any())).thenReturn(updated);

        AlcoholConsumptionRecord result = service.update(1L, updated);

        assertEquals("Updated", result.getRegion());
        verify(repository).existsById(1L);
        verify(repository).save(any());
    }

    @Test
    void updateShouldThrowWhenNotExists() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> service.update(1L, new AlcoholConsumptionRecord()));
    }

    @Test
    void deleteShouldCallRepository() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void deleteShouldThrowWhenNotExists() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> service.delete(1L));
    }
}
