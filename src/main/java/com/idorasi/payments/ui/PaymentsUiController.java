package com.idorasi.payments.ui;

import com.idorasi.payments.dto.PaymentDto;
import com.idorasi.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PaymentsUiController {

    @Autowired
    PaymentService paymentService;


    @GetMapping("/api/payments/ui/item-name/{itemName}/currency/{currency}/convert")
    public String getAndConvert(@PathVariable String itemName, @PathVariable String currency, Model model) {
        currency = currency.toUpperCase();
        PaymentDto paymentDto = paymentService.findAndConvert(itemName,currency);
        model.addAttribute("paymentDto",paymentDto);

        return "convert-result";
    }

}
