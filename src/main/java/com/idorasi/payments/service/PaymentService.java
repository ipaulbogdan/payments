package com.idorasi.payments.service;

import com.idorasi.payments.dto.PaymentDto;
import com.idorasi.payments.model.ExchangeRate;
import com.idorasi.payments.model.Payment;
import com.idorasi.payments.model.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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
                .orElseThrow(EntityNotFoundException::new);
    }

    public PaymentDto findAndConvert(String itemName, String symbol) {
        Payment payment = paymentRepository.findByItemName(itemName)
                .orElseThrow(EntityNotFoundException::new);
        Long targetCurrencyId = currencyService.findBySymbol(symbol).getId();
        Double rate = findConversionRate(payment,targetCurrencyId);

       return buildPaymentDto(payment,symbol,rate);
    }

    private Double findConversionRate(Payment payment,Long targetCurrencyId) {
        if (payment.getCurrencyId().equals(targetCurrencyId)) {
            return payment.getValue() * 1.0;
        }
        ExchangeRate exchangeRate = exchangeRateService.findByBaseAndPair(payment.getCurrencyId(),targetCurrencyId)
                    .orElseThrow(EntityNotFoundException::new);

        return payment.getValue() * exchangeRate.getRate();
    }

    private PaymentDto buildPaymentDto(Payment payment, String symbol, Double rate){
        return aPaymentDto()
                .withItemName(payment.getItemName())
                .withPairCurrency(symbol)
                .withValue(rate)
                .withDate(payment.getDate())
                .build();
    }
}
