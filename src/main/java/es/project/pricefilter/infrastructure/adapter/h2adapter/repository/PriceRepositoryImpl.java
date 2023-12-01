package es.project.pricefilter.infrastructure.adapter.h2adapter.repository;

import es.project.pricefilter.application.ports.output.repository.PriceRepository;
import es.project.pricefilter.domain.model.Price;
import es.project.pricefilter.infrastructure.adapter.h2adapter.entity.PriceRecord;
import es.project.pricefilter.infrastructure.mapper.PriceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Repository
@Slf4j
public class PriceRepositoryImpl implements PriceRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PriceMapper priceMapper;

    public PriceRepositoryImpl(JdbcTemplate jdbcTemplate, PriceMapper priceMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.priceMapper = priceMapper;
    }

    @Override
    public Optional<Price> filterPrices(int brandId, int productId, LocalDateTime applicationDate) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Object[] params = new Object[]{
                brandId,
                productId,
                applicationDate.format(dtf)
        };

        String sqlQuery = """
                    SELECT
                        p.BRAND_ID as brandId,
                        p.START_DATE as startDate,
                        p.END_DATE as endDate,
                        p.PRICE_LIST as priceList,
                        p.PRODUCT_ID as productId,
                        p.PRIORITY as priority,
                        p.PRICE as price,
                        p.CURR as currency
                    FROM PRICES as p
                    WHERE p.BRAND_ID = ? AND p.PRODUCT_ID = ? AND (?) BETWEEN p.START_DATE AND p.END_DATE
                    ORDER BY p.PRICE_LIST DESC
                    LIMIT 1;
                """;

        try {
            PriceRecord priceEntityResult = this.jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> (
                    new PriceRecord(
                            rs.getInt("brandId"),
                            rs.getTimestamp("startDate").toLocalDateTime(),
                            rs.getTimestamp("endDate").toLocalDateTime(),
                            rs.getInt("priceList"),
                            rs.getInt("productId"),
                            rs.getInt("priority"),
                            rs.getDouble("price"),
                            rs.getString("currency")
                    )
            ), params);

            return Optional.ofNullable(priceEntityResult)
                    .map(priceMapper::toPrice);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
