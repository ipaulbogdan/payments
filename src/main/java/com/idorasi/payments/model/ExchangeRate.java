package com.idorasi.payments.model;

import javax.persistence.*;;
import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(sourceCurrency, that.sourceCurrency) &&
                Objects.equals(targetCurrency, that.targetCurrency) &&
                Objects.equals(rate, that.rate) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceCurrency, targetCurrency, rate, date);
    }

}
