package ru.data.alcohol.alcoholconsumptionservice.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.data.alcohol.alcoholconsumptionservice.model.AlcoholConsumptionRecord;

import java.util.List;
import java.util.Optional;

@Repository
public class AlcoholConsumptionJdbcRepository implements CommonRepository<AlcoholConsumptionRecord, Long> {

    private final JdbcTemplate jdbcTemplate;

    public AlcoholConsumptionJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<AlcoholConsumptionRecord> rowMapper = (rs, rowNum) ->
            new AlcoholConsumptionRecord(
                    rs.getLong("id"),
                    rs.getString("region"),
                    rs.getInt("record_year"),
                    rs.getString("beverage_type"),
                    rs.getDouble("consumption_thousands_decaliters"),
                    rs.getDouble("consumption_liters_per_capita"),
                    rs.getDouble("consumption_pure_alcohol_per_capita")
            );

    @Override
    public List<AlcoholConsumptionRecord> findAll() {
        String sql = "SELECT * FROM alcohol_consumption ORDER BY id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<AlcoholConsumptionRecord> findById(Long id) {
        String sql = "SELECT * FROM alcohol_consumption WHERE id = ?";
        List<AlcoholConsumptionRecord> result = jdbcTemplate.query(sql, rowMapper, id);
        return result.stream().findFirst();
    }

    @Override
    public AlcoholConsumptionRecord save(AlcoholConsumptionRecord entity) {
        if (existsById(entity.getId())) {
            String updateSql = """
                    UPDATE alcohol_consumption
                    SET region = ?, record_year = ?, beverage_type = ?,
                        consumption_thousands_decaliters = ?,
                        consumption_liters_per_capita = ?,
                        consumption_pure_alcohol_per_capita = ?
                    WHERE id = ?
                    """;

            jdbcTemplate.update(
                    updateSql,
                    entity.getRegion(),
                    entity.getYear(),
                    entity.getBeverageType(),
                    entity.getConsumptionThousandsDecaliters(),
                    entity.getConsumptionLitersPerCapita(),
                    entity.getConsumptionPureAlcoholPerCapita(),
                    entity.getId()
            );
        } else {
            String insertSql = """
                    INSERT INTO alcohol_consumption
                    (id, region, record_year, beverage_type,
                     consumption_thousands_decaliters,
                     consumption_liters_per_capita,
                     consumption_pure_alcohol_per_capita)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                    """;

            jdbcTemplate.update(
                    insertSql,
                    entity.getId(),
                    entity.getRegion(),
                    entity.getYear(),
                    entity.getBeverageType(),
                    entity.getConsumptionThousandsDecaliters(),
                    entity.getConsumptionLitersPerCapita(),
                    entity.getConsumptionPureAlcoholPerCapita()
            );
        }

        return entity;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM alcohol_consumption WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM alcohol_consumption WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM alcohol_consumption";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count == null ? 0 : count;
    }
}