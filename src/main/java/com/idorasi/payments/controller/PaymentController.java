package com.idorasi.payments.controller;



import com.idorasi.payments.model.Payment;
import com.idorasi.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/add")
    public Payment addPayment(@RequestBody Payment payment){
        return paymentService.addPayment(payment);
    }
    @DeleteMapping("/delete")
    public void deletePayment(@RequestParam long id){
        paymentService.deletePayment(id);
    }

    @DeleteMapping("/delete/all")
    public void deleteAll(){
        paymentService.deleteAll();
    }

    @GetMapping("/view/all")
    public List<Payment> viewPayments(){
        return paymentService.getPayments();
    }

    @GetMapping("/get/{itemName}")
    public Payment getByItemName(@PathVariable String itemName){
        return paymentService.findByItemName(itemName);
    }



}
