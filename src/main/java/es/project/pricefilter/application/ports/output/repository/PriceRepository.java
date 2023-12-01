package es.project.pricefilter.application.ports.output.repository;

import es.project.pricefilter.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {
    Optional<Price> filterPrices(int brandId, int productId, LocalDateTime applicationDate);
}
