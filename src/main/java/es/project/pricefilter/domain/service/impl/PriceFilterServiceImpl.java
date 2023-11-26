package es.project.pricefilter.domain.service.impl;

import es.project.pricefilter.domain.service.PriceFilterService;
import es.project.pricefilter.infrastructure.adapter.api.dto.input.FilterInput;
import es.project.pricefilter.infrastructure.adapter.api.dto.output.PriceOutput;
import es.project.pricefilter.infrastructure.adapter.persistence.repository.PriceRepository;
import es.project.pricefilter.infrastructure.mapper.PriceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceFilterServiceImpl implements PriceFilterService {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    public PriceFilterServiceImpl(PriceRepository priceRepository, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    @Override
    public PriceOutput filterPrices(FilterInput filter) {
        return priceRepository.filterPrices(filter)
                .map(priceMapper::toPriceOutput)
                .orElse(null);
    }
}
