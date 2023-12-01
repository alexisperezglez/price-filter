package es.project.pricefilter.infrastructure.adapter.h2adapter.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PriceEntity {

    private int brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int priceList;
    private int productId;
    private int priority;
    private double price;
    private String currency;
}
