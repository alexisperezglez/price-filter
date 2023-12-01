package es.project.pricefilter.infrastructure.adapter.restadapter.api.v1;

import es.project.pricefilter.application.service.PriceFilterService;
import es.project.pricefilter.infrastructure.adapter.restadapter.dto.output.PriceOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/v1/prices")
@Slf4j
@Validated
@Tag(name = "Price Filter", description = "Price Filter API")
public class PriceFilterController {

    private final PriceFilterService priceFilterService;

    public PriceFilterController(PriceFilterService priceFilterService) {
        this.priceFilterService = priceFilterService;
    }

    @Operation(summary = "Filter prices",
            description = "Filter prices by brandId, productId and applicationDate",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(
                            name = "brandId",
                            in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
                            required = true,
                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                    type = "integer",
                                    format = "int32"
                            ),
                            description = "Brand identifier",
                            example = "1"
                    ),
                    @io.swagger.v3.oas.annotations.Parameter(
                            name = "productId",
                            in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
                            required = true,
                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                    type = "integer",
                                    format = "int32"
                            ),
                            description = "Product identifier",
                            example = "35455"
                    ),
                    @io.swagger.v3.oas.annotations.Parameter(
                            name = "applicationDate",
                            in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
                            required = true,
                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                    type = "string",
                                    format = "date-time"
                            ),
                            description = "Application date",
                            example = "2020-06-14 10:00:00"
                    )
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(
                                            implementation = PriceOutput.class
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(
                                            implementation = ProblemDetail.class
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(
                                            implementation = ProblemDetail.class
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<PriceOutput> filterPrices(
            @RequestParam @Min(value = 1) int brandId,
            @RequestParam @Min(value = 1) int productId,
            @RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd[ HH:mm:ss][.SS][.SSS]")
            LocalDateTime applicationDate
    ) {
        PriceOutput price = priceFilterService.filterPrices(brandId, productId, applicationDate);

        if (Optional.ofNullable(price).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(price);
    }
}
