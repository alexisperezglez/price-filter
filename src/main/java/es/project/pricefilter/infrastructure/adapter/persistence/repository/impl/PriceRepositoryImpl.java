package es.project.pricefilter.infrastructure.adapter.persistence.repository.impl;

import es.project.pricefilter.infrastructure.adapter.api.dto.input.FilterInput;
import es.project.pricefilter.infrastructure.adapter.persistence.entity.Price;
import es.project.pricefilter.infrastructure.adapter.persistence.repository.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Repository
@Slf4j
public class PriceRepositoryImpl implements PriceRepository {

    private final JdbcTemplate jdbcTemplate;

    public PriceRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Price> filterPrices(FilterInput filter) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Object[] params = new Object[]{
                filter.getBrandId(),
                filter.getProductId(),
                filter.getApplicationDate().format(dtf)
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
            Price priceResult = this.jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> {
                Price price = new Price();
                price.setBrandId(rs.getInt("brandId"));
                price.setStartDate(rs.getTimestamp("startDate").toLocalDateTime());
                price.setEndDate(rs.getTimestamp("endDate").toLocalDateTime());
                price.setPriceList(rs.getInt("priceList"));
                price.setProductId(rs.getInt("productId"));
                price.setPriority(rs.getInt("priority"));
                price.setPrice(rs.getDouble("price"));
                price.setCurrency(rs.getString("currency"));
                return price;
            }, params);

            return Optional.ofNullable(priceResult);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }
}
