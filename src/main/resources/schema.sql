CREATE TABLE IF NOT EXISTS alcohol_consumption (
                                                   id BIGINT PRIMARY KEY,
                                                   region VARCHAR(255) NOT NULL,
    record_year INT NOT NULL,
    beverage_type VARCHAR(255) NOT NULL,
    consumption_thousands_decaliters DOUBLE,
    consumption_liters_per_capita DOUBLE,
    consumption_pure_alcohol_per_capita DOUBLE
    );