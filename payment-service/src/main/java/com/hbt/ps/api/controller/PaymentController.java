package com.hbt.ps.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hbt.ps.api.models.entity.Payment;
import com.hbt.ps.api.models.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private IPaymentService service;

    @PostMapping("/doPayment")
    public Payment doPayment(@RequestBody Payment payment) throws Exception {
        return service.doPayment(payment);
    }

    @GetMapping("/{orderId}")
    public Payment findByOrderId(@PathVariable int orderId) throws JsonProcessingException {
        return service.findByOrderId(orderId);
    }

    @GetMapping("/all")
    public List<Payment> findOrders(){
        return service.findOrders();
    }


}
