package es.project.pricefilter.infrastructure.adapter.api.v1;

import es.project.pricefilter.domain.service.PriceFilterService;
import es.project.pricefilter.infrastructure.adapter.api.dto.input.FilterInput;
import es.project.pricefilter.infrastructure.adapter.api.dto.output.PriceOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/price-filter")
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
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter request input",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                    implementation = FilterInput.class
                            )
                    )
            ),
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
    @PostMapping
    public ResponseEntity<PriceOutput> filterPrices(@RequestBody @Valid FilterInput filterInput) {
        PriceOutput price = priceFilterService.filterPrices(filterInput);

        if (Optional.ofNullable(price).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(price);
    }
}
