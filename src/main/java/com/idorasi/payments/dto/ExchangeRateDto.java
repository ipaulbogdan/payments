package com.idorasi.payments.dto;

import java.time.LocalDate;
import java.util.HashMap;

public class ExchangeRateDto {

    private String base;
    private LocalDate date;
    private HashMap<String,Double> rates;


    public ExchangeRateDto(){}

    public String getBase()  {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }

    public void setRates(HashMap<String, Double> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "ExchangeRateDto{" +
                "base='" + base + '\'' +
                ", date=" + date +
                ", rates=" + rates +
                '}';
    }


}
