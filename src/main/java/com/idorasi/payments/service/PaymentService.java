package com.idorasi.payments.service;

import com.idorasi.payments.dto.PaymentDto;
import com.idorasi.payments.model.ExchangeRate;
import com.idorasi.payments.model.Payment;
import com.idorasi.payments.model.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
     private CurrencyService currencyService;

    @Autowired
    private ExchangeRateService exchangeRateService;


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
        return paymentRepository.findByItemName(itemName)
                .orElseThrow(IllegalArgumentException::new);
    }


    public PaymentDto findAndConvert(String itemName, String symbol) {
        Payment payment = paymentRepository.findByItemName(itemName)
                .orElseThrow(IllegalArgumentException::new);
        Long targetCurrencyId = currencyService.findBySymbol(symbol).getId();
        ExchangeRate exchangeRate = exchangeRateService.findByBaseAndPair(payment.getCurrencyId(),targetCurrencyId);

        return new PaymentDto(itemName
                ,symbol
                ,(payment.getValue()*exchangeRate.getRate())
                ,payment.getDate()
                ,exchangeRate.getDate());
    }
}
