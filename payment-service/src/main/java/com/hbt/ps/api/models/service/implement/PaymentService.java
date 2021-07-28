package com.hbt.ps.api.models.service.implement;

import brave.Tracer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbt.ps.api.models.entity.Payment;
import com.hbt.ps.api.models.repository.PaymentRepository;
import com.hbt.ps.api.models.service.IPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class PaymentService implements IPaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private Tracer tracer;

    @Override
    public Payment doPayment(Payment payment) throws Exception {
        log.info(this.getClass().getSimpleName() + " Inicio de ejecucion del metodo doPayment");
        try {
            payment.setPaymentStatus(paymentProcessing());
            payment.setTransactionId(UUID.randomUUID().toString());
            log.info(payment.toString());
            log.info("Solicitud PaymentService: {}", new ObjectMapper().writeValueAsString(payment));
            return repository.save(payment);
        }catch (Exception e){
            String message = this.getClass().getSimpleName() + " Error en el metodo save: " + e.getLocalizedMessage() + " causa: " + e.getCause() + " StackTrace: " + e.getStackTrace();
            tracer.currentSpan().tag("error.message", message);
            log.error(message);
            throw new Exception(message);
        }
    }

    @Override
    public String paymentProcessing() {
        return new Random().nextBoolean() ? "Success" : "NonSuccess";
    }

    @Override
    public Payment findByOrderId(int orderId) throws JsonProcessingException {
        Payment payment = repository.findByOrderId(orderId);
        log.info("Solicitud PaymentService - findByOrderId: {}", new ObjectMapper().writeValueAsString(payment));
        return payment;
    }

    @Override
    public List<Payment> findOrders() {
        return repository.findAll();
    }
}
