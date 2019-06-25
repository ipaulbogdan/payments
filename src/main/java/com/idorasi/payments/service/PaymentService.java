package com.idorasi.payments.service;

import com.idorasi.payments.model.Payment;
import com.idorasi.payments.model.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;


    public Payment addPayment(Payment payment){
        return paymentRepository.save(payment);
    }

    public void deletePayment(long id){
        paymentRepository.deleteById(id);
    }

    public void deleteAll(){
        paymentRepository.deleteAll();

    }
    public List<Payment> getPayments(){
        return paymentRepository.findAll();
    }

    public Payment findByItemName(String itemName){
        return paymentRepository.findByItemName(itemName);
    }






}
