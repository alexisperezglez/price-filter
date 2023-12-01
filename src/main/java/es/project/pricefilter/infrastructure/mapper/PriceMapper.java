package es.project.pricefilter.infrastructure.mapper;

import es.project.pricefilter.domain.model.Price;
import es.project.pricefilter.infrastructure.adapter.h2adapter.entity.PriceRecord;
import es.project.pricefilter.infrastructure.adapter.restadapter.dto.output.PriceOutput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceOutput toPriceOutput(Price source);

    Price toPrice(PriceRecord source);

}
