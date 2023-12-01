package es.project.pricefilter.application.usecases;

import es.project.pricefilter.infrastructure.adapter.restadapter.dto.output.PriceOutput;

import java.time.LocalDateTime;

public interface PriceFilterUseCase {

    PriceOutput filterPrices(int brandId, int productId, LocalDateTime applicationDate);
}
