package com.idorasi.payments.dto;

import java.time.LocalDate;

public class PaymentDto {

    private String itemName;
    private String pairCurrency;
    private Double value;
    private LocalDate date;

    public PaymentDto(String itemName, String pairCurrency, Double value, LocalDate date) {
        this.itemName = itemName;
        this.pairCurrency = pairCurrency;
        this.value = value;
        this.date = date;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPairCurrency() {
        return pairCurrency;
    }

    public void setPairCurrency(String pairCurrency) {
        this.pairCurrency = pairCurrency;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
