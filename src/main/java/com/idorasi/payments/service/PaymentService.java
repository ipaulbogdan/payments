package com.idorasi.payments.service;

import com.idorasi.payments.dto.PaymentDto;
import com.idorasi.payments.model.ExchangeRate;
import com.idorasi.payments.model.Payment;
import com.idorasi.payments.model.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.idorasi.payments.dto.PaymentDtoBuilder.aPaymentDto;

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

        Optional<ExchangeRate> exchangeRate = exchangeRateService.findByBaseAndPair(payment.getCurrencyId(),targetCurrencyId);

        return exchangeRate.map(rate -> buildPaymentDto(payment, symbol, rate.getRate(),rate.getDate()))
                .orElseGet(() -> buildPaymentDto(payment, symbol,1.0,null));

    }
    private PaymentDto buildPaymentDto(Payment payment, String symbol, Double rate, LocalDate date){
        Double value = payment.getValue() * rate;
        return aPaymentDto()
                .withItemName(payment.getItemName())
                .withPairCurrency(symbol)
                .withValue(value)
                .withDate(payment.getDate())
                .withRateDate(date)
                .build();
    }
}
