package com.idorasi.payments.controller;

import com.idorasi.payments.dto.PaymentDto;
import com.idorasi.payments.model.Payment;
import com.idorasi.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Payment addPayment(@RequestBody Payment payment) {
        return paymentService.addPayment(payment);
    }

    @DeleteMapping("/item-name/{id}")
    public void deletePayment(@PathVariable long id) { //use itemName
        paymentService.deletePayment(id);
    }

    @DeleteMapping
    public void deleteAll() {
        paymentService.deleteAll();
    }

    @GetMapping
    public List<Payment> viewPayments() {
        return paymentService.getPayments();
    }

    @GetMapping("/{itemName}")
    public Payment getByItemName(@PathVariable String itemName) {
        return paymentService.findByItemName(itemName);
    }

    @GetMapping("/item-name/{itemName}/currency/{symbol}/convert")
    public PaymentDto getAndConvert(@PathVariable String itemName, @PathVariable String symbol) {
       return(paymentService.findAndConvert(itemName, symbol));
    }

}

