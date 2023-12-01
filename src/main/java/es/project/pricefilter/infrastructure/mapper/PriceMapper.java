package es.project.pricefilter.infrastructure.mapper;

import es.project.pricefilter.infrastructure.adapter.h2adapter.entity.PriceEntity;
import es.project.pricefilter.infrastructure.adapter.restadapter.dto.output.PriceOutput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceOutput toPriceOutput(PriceEntity source);

}
