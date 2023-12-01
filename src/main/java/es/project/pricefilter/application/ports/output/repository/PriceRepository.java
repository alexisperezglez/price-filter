package es.project.pricefilter.application.ports.output.repository;

import es.project.pricefilter.infrastructure.adapter.h2adapter.entity.PriceEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {
    Optional<PriceEntity> filterPrices(int brandId, int productId, LocalDateTime applicationDate);
}
