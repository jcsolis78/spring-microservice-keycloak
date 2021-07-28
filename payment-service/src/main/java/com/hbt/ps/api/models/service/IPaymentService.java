package com.hbt.ps.api.models.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hbt.ps.api.models.entity.Payment;

import java.util.List;

public interface IPaymentService {

    Payment doPayment(Payment payment) throws Exception;
    String paymentProcessing();

    Payment findByOrderId(int orderId) throws JsonProcessingException;

    List<Payment> findOrders();
}
