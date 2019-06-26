package com.idorasi.payments.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected Long id;

    @Column
    private String itemName;

    @Column
    private Long currencyId;

    @Column
    private Long value;

    @Column
    private LocalDate date = LocalDate.now();

    public Payment(){
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void date(LocalDate date) {
        this.date = date;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
