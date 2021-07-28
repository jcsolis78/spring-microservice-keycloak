package com.hbt.os.api.models.service.implement;

import brave.Tracer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbt.os.api.models.commons.Payment;
import com.hbt.os.api.models.commons.TransactionRequest;
import com.hbt.os.api.models.commons.TransactionResponse;
import com.hbt.os.api.models.entity.Order;
import com.hbt.os.api.models.repository.OrderRepository;
import com.hbt.os.api.models.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RefreshScope
public class OrderService implements IOrderService {

    private String CLASS_NAME = this.getClass().getSimpleName();

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    @Lazy
    private RestTemplate template;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String ENDPOINT_URL;

    @Autowired
    private Tracer tracer;

    @Override
    public TransactionResponse save(TransactionRequest request) throws Exception {
        log.info(CLASS_NAME + " Inicio almacenamiento de datos metodo SAVE");
        try {
            String response = "";
            Order order = orderRepository.save(request.getOrder());
            Payment payment = request.getPayment();
            payment.setOrderId(order.getId());
            payment.setAmount(order.getPrice());

            //Rest Call
            log.info("Solicitud OrderService: {}", new ObjectMapper().writeValueAsString(request));
            //Payment paymentResponse = template.postForObject("http://localhost:8182/api/payment/doPayment",payment,Payment.class);
            Payment paymentResponse = template.postForObject(ENDPOINT_URL,payment,Payment.class);

            log.info("Respuesta de Payment-service desde llamada REST en OrderService : {}", new ObjectMapper().writeValueAsString(paymentResponse));
            log.debug(CLASS_NAME, paymentResponse.getPaymentStatus());
            response = paymentResponse.getPaymentStatus().equals("Success") ? "Pago procesado satisfactoriamente y orden realizada" : "hay un error en la API de pago, pedido agregado al carrito";

            return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), response);
        }catch (Exception e){
            String message = CLASS_NAME + " Error en el metodo save: " + e.getLocalizedMessage() + " causa: " + e.getCause() + " StackTrace: " + e.getStackTrace();
            tracer.currentSpan().tag("error.message", message);
            log.error(message);
            throw new Exception(message);
        }
    }

    @Override
    public List<Order> findByAllOrder() {
        return orderRepository.findAll();
    }
}
