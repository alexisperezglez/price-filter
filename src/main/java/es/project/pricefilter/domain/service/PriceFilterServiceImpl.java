package es.project.pricefilter.domain.service;

import es.project.pricefilter.application.ports.output.repository.PriceRepository;
import es.project.pricefilter.application.service.PriceFilterService;
import es.project.pricefilter.infrastructure.adapter.restadapter.dto.output.PriceOutput;
import es.project.pricefilter.infrastructure.mapper.PriceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public PriceOutput filterPrices(int brandId, int productId, LocalDateTime applicationDate) {
        return priceRepository.filterPrices(brandId, productId, applicationDate)
                .map(priceMapper::toPriceOutput)
                .orElse(null);
    }
}
