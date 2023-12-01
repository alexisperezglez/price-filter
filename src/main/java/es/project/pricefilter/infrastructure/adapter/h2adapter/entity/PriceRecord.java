package es.project.pricefilter.infrastructure.adapter.h2adapter.entity;

import java.time.LocalDateTime;

public record PriceRecord(int brandId, LocalDateTime startDate, LocalDateTime endDate, int priceList, int productId,
                          int priority, double price, String currency) {
}
