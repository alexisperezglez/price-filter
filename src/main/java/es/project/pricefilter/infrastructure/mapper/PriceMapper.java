package es.project.pricefilter.infrastructure.mapper;

import es.project.pricefilter.infrastructure.adapter.api.dto.output.PriceOutput;
import es.project.pricefilter.infrastructure.adapter.persistence.entity.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceOutput toPriceOutput(Price source);

}
