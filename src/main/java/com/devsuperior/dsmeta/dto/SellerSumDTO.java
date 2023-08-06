package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SellerSumProjection;

public class SellerSumDTO {
    private String name;
    private Double total;

    public SellerSumDTO(String name, Double total) {
        this.name = name;
        this.total = total;
    }

    public SellerSumDTO(SellerSumProjection projection) {
        name = projection.getName();
        total = projection.getTotal();
    }

    public String getName() {
        return name;
    }

    public Double getTotal() {
        return total;
    }
}
