package com.hbt.gateway.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/orderFallback")
    public Mono<String> orderServiceFallback(){
        return Mono.just("El servicio de orden esta tomando mucho tiempo en responder o esta caido, por favor intentelo mas tarde");
    }

    @RequestMapping("/paymentFallback")
    public Mono<String> paymentServiceFallback(){
        return Mono.just("El servicio de pagos esta tomando mucho tiempo en responder o esta caido, por favor intentelo mas tarde");
    }

}
