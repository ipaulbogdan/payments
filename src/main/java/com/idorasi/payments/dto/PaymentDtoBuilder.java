package com.idorasi.payments.dto;

import java.time.LocalDate;

public final class PaymentDtoBuilder {
    private String itemName;
    private String pairCurrency;
    private Double value;
    private LocalDate date;
    private LocalDate rateDate;

    private PaymentDtoBuilder() {
    }

    public static PaymentDtoBuilder aPaymentDto() {
        return new PaymentDtoBuilder();
    }

    public PaymentDtoBuilder withItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public PaymentDtoBuilder withPairCurrency(String pairCurrency) {
        this.pairCurrency = pairCurrency;
        return this;
    }

    public PaymentDtoBuilder withValue(Double value) {
        this.value = value;
        return this;
    }

    public PaymentDtoBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public PaymentDto build() {
        return new PaymentDto(itemName, pairCurrency, value, date);
    }
}
