package ru.data.alcohol.alcoholconsumptionservice.dto;

public class AlcoholConsumptionDto {

    private Long id;
    private String region;
    private Integer year;
    private String beverageType;
    private Double consumptionThousandsDecaliters;
    private Double consumptionLitersPerCapita;
    private Double consumptionPureAlcoholPerCapita;

    public AlcoholConsumptionDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getBeverageType() {
        return beverageType;
    }

    public void setBeverageType(String beverageType) {
        this.beverageType = beverageType;
    }

    public Double getConsumptionThousandsDecaliters() {
        return consumptionThousandsDecaliters;
    }

    public void setConsumptionThousandsDecaliters(Double consumptionThousandsDecaliters) {
        this.consumptionThousandsDecaliters = consumptionThousandsDecaliters;
    }

    public Double getConsumptionLitersPerCapita() {
        return consumptionLitersPerCapita;
    }

    public void setConsumptionLitersPerCapita(Double consumptionLitersPerCapita) {
        this.consumptionLitersPerCapita = consumptionLitersPerCapita;
    }

    public Double getConsumptionPureAlcoholPerCapita() {
        return consumptionPureAlcoholPerCapita;
    }

    public void setConsumptionPureAlcoholPerCapita(Double consumptionPureAlcoholPerCapita) {
        this.consumptionPureAlcoholPerCapita = consumptionPureAlcoholPerCapita;
    }
}
