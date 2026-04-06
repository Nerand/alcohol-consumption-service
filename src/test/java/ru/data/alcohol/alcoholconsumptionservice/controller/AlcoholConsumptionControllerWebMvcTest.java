package ru.data.alcohol.alcoholconsumptionservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.data.alcohol.alcoholconsumptionservice.dto.AlcoholConsumptionDto;
import ru.data.alcohol.alcoholconsumptionservice.model.AlcoholConsumptionRecord;
import ru.data.alcohol.alcoholconsumptionservice.security.DatabaseSecurityConfig;
import ru.data.alcohol.alcoholconsumptionservice.service.AlcoholConsumptionServiceInterface;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// WebMvcTest для Л10
// Проверяем только контроллер и HTTP-слой
@WebMvcTest(AlcoholConsumptionController.class)
@Import(DatabaseSecurityConfig.class)
class AlcoholConsumptionControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AlcoholConsumptionServiceInterface service;

    @MockBean
    private ru.data.alcohol.alcoholconsumptionservice.security.DatabaseUserDetailsService databaseUserDetailsService;

    @Test
    void shouldReturn401WithoutAuth() throws Exception {
        mockMvc.perform(get("/api/alcohol-consumption/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getByIdShouldReturnRecord() throws Exception {
        AlcoholConsumptionRecord record = new AlcoholConsumptionRecord();
        record.setId(1L);
        record.setRegion("Belgorod Oblast");
        record.setYear(2017);
        record.setBeverageType("Wine");
        record.setConsumptionThousandsDecaliters(278.27);
        record.setConsumptionLitersPerCapita(1.79);
        record.setConsumptionPureAlcoholPerCapita(0.2);

        when(service.getById(1L)).thenReturn(record);

        mockMvc.perform(get("/api/alcohol-consumption/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.region").value("Belgorod Oblast"))
                .andExpect(jsonPath("$.year").value(2017))
                .andExpect(jsonPath("$.beverageType").value("Wine"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getAllShouldReturnList() throws Exception {
        AlcoholConsumptionRecord first = new AlcoholConsumptionRecord();
        first.setId(1L);
        first.setRegion("Region 1");
        first.setYear(2020);
        first.setBeverageType("Beer");

        AlcoholConsumptionRecord second = new AlcoholConsumptionRecord();
        second.setId(2L);
        second.setRegion("Region 2");
        second.setYear(2021);
        second.setBeverageType("Vodka");

        when(service.getAll()).thenReturn(List.of(first, second));

        mockMvc.perform(get("/api/alcohol-consumption"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].region").value("Region 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].region").value("Region 2"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createShouldReturnCreatedRecord() throws Exception {
        AlcoholConsumptionDto dto = new AlcoholConsumptionDto();
        dto.setRegion("New region");
        dto.setYear(2026);
        dto.setBeverageType("Beer");
        dto.setConsumptionThousandsDecaliters(10.5);
        dto.setConsumptionLitersPerCapita(1.2);
        dto.setConsumptionPureAlcoholPerCapita(0.3);

        AlcoholConsumptionRecord saved = new AlcoholConsumptionRecord();
        saved.setId(100L);
        saved.setRegion("New region");
        saved.setYear(2026);
        saved.setBeverageType("Beer");
        saved.setConsumptionThousandsDecaliters(10.5);
        saved.setConsumptionLitersPerCapita(1.2);
        saved.setConsumptionPureAlcoholPerCapita(0.3);

        when(service.create(any(AlcoholConsumptionRecord.class))).thenReturn(saved);

        mockMvc.perform(post("/api/alcohol-consumption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.region").value("New region"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateShouldReturnUpdatedRecord() throws Exception {
        AlcoholConsumptionDto dto = new AlcoholConsumptionDto();
        dto.setRegion("Updated region");
        dto.setYear(2027);
        dto.setBeverageType("Vodka");
        dto.setConsumptionThousandsDecaliters(20.0);
        dto.setConsumptionLitersPerCapita(2.5);
        dto.setConsumptionPureAlcoholPerCapita(0.8);

        AlcoholConsumptionRecord updated = new AlcoholConsumptionRecord();
        updated.setId(1L);
        updated.setRegion("Updated region");
        updated.setYear(2027);
        updated.setBeverageType("Vodka");
        updated.setConsumptionThousandsDecaliters(20.0);
        updated.setConsumptionLitersPerCapita(2.5);
        updated.setConsumptionPureAlcoholPerCapita(0.8);

        when(service.update(eq(1L), any(AlcoholConsumptionRecord.class))).thenReturn(updated);

        mockMvc.perform(put("/api/alcohol-consumption/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.region").value("Updated region"))
                .andExpect(jsonPath("$.year").value(2027));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteShouldReturn204() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/alcohol-consumption/1"))
                .andExpect(status().isNoContent());
    }
}
