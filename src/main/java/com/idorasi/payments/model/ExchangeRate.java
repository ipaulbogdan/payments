package com.idorasi.payments.model;

import javax.persistence.*;;
import java.time.LocalDate;

@Entity
@Table(name="exchangerates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected Long id;

    @Column(name="base_currency_id")
    private Long sourceCurrency;

    @Column(name="pair_currency_id")
    private Long targetCurrency;

    @Column
    private Double rate;

    @Column
    private LocalDate date = LocalDate.now();


    public ExchangeRate(){}


    public Long getId() {
        return id;
    }

    public Long getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(Long sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public Long getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Long targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
