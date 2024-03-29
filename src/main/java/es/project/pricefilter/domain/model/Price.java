package es.project.pricefilter.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Price {

    private int brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int priceList;
    private int productId;
    private int priority;
    private double price;
    private String currency;
}
