package es.project.pricefilter.infrastructure.adapter.persistence.repository;

import es.project.pricefilter.infrastructure.adapter.api.dto.input.FilterInput;
import es.project.pricefilter.infrastructure.adapter.persistence.entity.Price;

import java.util.Optional;

public interface PriceRepository {
    Optional<Price> filterPrices(FilterInput filter);
}
