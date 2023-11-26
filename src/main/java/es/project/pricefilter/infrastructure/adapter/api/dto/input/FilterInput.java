package es.project.pricefilter.infrastructure.adapter.api.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Data
@Validated
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Schema(name = "FilterInput", description = "Filter request input")
public class FilterInput {

    @NotNull(message = "applicationDate must not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd[ HH:mm:ss][.SS][.SSS]")
    @Schema(name = "applicationDate", requiredMode = Schema.RequiredMode.REQUIRED, description = "Application date", example = "2022-01-01 00:00:00.000")
    private LocalDateTime applicationDate;
    @NotNull(message = "productId must not be null")
    @Min(value = 1, message = "productId must be greater than 0")
    @Schema(name = "productId", requiredMode = Schema.RequiredMode.REQUIRED, description = "Product identifier", example = "35455")
    private int productId;
    @NotNull(message = "brandId must not be null")
    @Min(value = 1, message = "brandId must be greater than 0")
    @Schema(name = "brandId", requiredMode = Schema.RequiredMode.REQUIRED, description = "Brand identifier", example = "1")
    private int brandId;
}
