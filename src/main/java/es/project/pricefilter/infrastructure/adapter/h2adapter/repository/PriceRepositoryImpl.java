package es.project.pricefilter.infrastructure.adapter.h2adapter.repository;

import es.project.pricefilter.application.ports.output.repository.PriceRepository;
import es.project.pricefilter.infrastructure.adapter.h2adapter.entity.PriceEntity;
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

    public PriceRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<PriceEntity> filterPrices(int brandId, int productId, LocalDateTime applicationDate) {

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
                        p.PRICE as priceEntity,
                        p.CURR as currency
                    FROM PRICES as p
                    WHERE p.BRAND_ID = ? AND p.PRODUCT_ID = ? AND (?) BETWEEN p.START_DATE AND p.END_DATE
                    ORDER BY p.PRICE_LIST DESC
                    LIMIT 1;
                """;

        try {
            PriceEntity priceEntityResult = this.jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> {
                PriceEntity priceEntity = new PriceEntity();
                priceEntity.setBrandId(rs.getInt("brandId"));
                priceEntity.setStartDate(rs.getTimestamp("startDate").toLocalDateTime());
                priceEntity.setEndDate(rs.getTimestamp("endDate").toLocalDateTime());
                priceEntity.setPriceList(rs.getInt("priceList"));
                priceEntity.setProductId(rs.getInt("productId"));
                priceEntity.setPriority(rs.getInt("priority"));
                priceEntity.setPrice(rs.getDouble("priceEntity"));
                priceEntity.setCurrency(rs.getString("currency"));
                return priceEntity;
            }, params);

            return Optional.ofNullable(priceEntityResult);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }
}
