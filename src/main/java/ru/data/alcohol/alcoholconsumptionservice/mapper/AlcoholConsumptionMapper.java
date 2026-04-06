package ru.data.alcohol.alcoholconsumptionservice.mapper;

import ru.data.alcohol.alcoholconsumptionservice.dto.AlcoholConsumptionDto;
import ru.data.alcohol.alcoholconsumptionservice.model.AlcoholConsumptionRecord;

public class AlcoholConsumptionMapper {

    public static AlcoholConsumptionDto toDto(AlcoholConsumptionRecord entity) {
        AlcoholConsumptionDto dto = new AlcoholConsumptionDto();
        dto.setId(entity.getId());
        dto.setRegion(entity.getRegion());
        dto.setYear(entity.getYear());
        dto.setBeverageType(entity.getBeverageType());
        dto.setConsumptionThousandsDecaliters(entity.getConsumptionThousandsDecaliters());
        dto.setConsumptionLitersPerCapita(entity.getConsumptionLitersPerCapita());
        dto.setConsumptionPureAlcoholPerCapita(entity.getConsumptionPureAlcoholPerCapita());
        return dto;
    }

    public static AlcoholConsumptionRecord toEntity(AlcoholConsumptionDto dto) {
        AlcoholConsumptionRecord entity = new AlcoholConsumptionRecord();
        entity.setId(dto.getId());
        entity.setRegion(dto.getRegion());
        entity.setYear(dto.getYear());
        entity.setBeverageType(dto.getBeverageType());
        entity.setConsumptionThousandsDecaliters(dto.getConsumptionThousandsDecaliters());
        entity.setConsumptionLitersPerCapita(dto.getConsumptionLitersPerCapita());
        entity.setConsumptionPureAlcoholPerCapita(dto.getConsumptionPureAlcoholPerCapita());
        return entity;
    }
}
