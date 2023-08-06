package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;

import java.time.LocalDate;

public class SaleDTO {
    private Long id;
    private LocalDate date;
    private Double amount;
    private String name;

    public SaleDTO(Long id, LocalDate date, Double amount, String name) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.name = name;
    }

    public SaleDTO(Sale entity) {
        id = entity.getId();
        date = entity.getDate();
        amount = entity.getAmount();
        name = entity.getSeller().getName();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }
}
