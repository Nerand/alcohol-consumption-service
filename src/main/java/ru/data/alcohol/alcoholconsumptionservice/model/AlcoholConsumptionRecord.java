package ru.data.alcohol.alcoholconsumptionservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AlcoholConsumptionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String region;

    @Column(name = "record_year", nullable = false)
    private Integer year;

    @Column(name = "beverage_type", nullable = false)
    private String beverageType;

    @Column(name = "consumption_thousands_decaliters")
    private Double consumptionThousandsDecaliters;

    @Column(name = "consumption_liters_per_capita")
    private Double consumptionLitersPerCapita;

    @Column(name = "consumption_pure_alcohol_per_capita")
    private Double consumptionPureAlcoholPerCapita;

    public AlcoholConsumptionRecord() {
    }

    public AlcoholConsumptionRecord(Long id,
                                    String region,
                                    Integer year,
                                    String beverageType,
                                    Double consumptionThousandsDecaliters,
                                    Double consumptionLitersPerCapita,
                                    Double consumptionPureAlcoholPerCapita) {
        this.id = id;
        this.region = region;
        this.year = year;
        this.beverageType = beverageType;
        this.consumptionThousandsDecaliters = consumptionThousandsDecaliters;
        this.consumptionLitersPerCapita = consumptionLitersPerCapita;
        this.consumptionPureAlcoholPerCapita = consumptionPureAlcoholPerCapita;
    }

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