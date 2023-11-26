package es.project.pricefilter.infrastructure.adapter.api.dto.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Price Output", name = "PriceOutput")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class PriceOutput {

    @Schema(name = "productId", requiredMode = Schema.RequiredMode.REQUIRED, description = "Product identifier", example = "35455")
    private int productId;
    @Schema(name = "brandId", requiredMode = Schema.RequiredMode.REQUIRED, description = "Brand identifier", example = "1")
    private int brandId;
    @Schema(name = "priceList", requiredMode = Schema.RequiredMode.REQUIRED, description = "Applicable pricing rate identifier", example = "35455")
    private int priceList;
    @Schema(name = "startDate", requiredMode = Schema.RequiredMode.REQUIRED, description = "date range in which the indicated price rate applies.", example = "2020-06-14 10:00:00")
    private LocalDateTime startDate;
    @Schema(name = "endDate", requiredMode = Schema.RequiredMode.REQUIRED, description = "date range in which the indicated price rate applies.", example = "2020-06-14 10:00:00")
    private LocalDateTime endDate;
    @Schema(name = "price", requiredMode = Schema.RequiredMode.REQUIRED, description = "Price", example = "30.50")
    private double price;
}
