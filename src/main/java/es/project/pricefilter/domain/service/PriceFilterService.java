package es.project.pricefilter.domain.service;

import es.project.pricefilter.infrastructure.adapter.api.dto.input.FilterInput;
import es.project.pricefilter.infrastructure.adapter.api.dto.output.PriceOutput;

public interface PriceFilterService {
    PriceOutput filterPrices(FilterInput filter);
}
