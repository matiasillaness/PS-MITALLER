package com.mitaller.modulos.cobros.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class MesTotalDTO {
    private int year;
    private int month;
    private BigDecimal total;

    public MesTotalDTO(int year, int month, BigDecimal total) {
        this.year = year;
        this.month = month;
        this.total = total;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public BigDecimal getTotal() {
        return total;
    }

}
