package com.hbt.os.api.controller;

import com.hbt.os.api.models.commons.TransactionRequest;
import com.hbt.os.api.models.commons.TransactionResponse;
import com.hbt.os.api.models.entity.Order;
import com.hbt.os.api.models.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private IOrderService service;

    @PostMapping("/save")
    public TransactionResponse saveOrder(@RequestBody TransactionRequest request) throws Exception {
        return service.save(request);
    }

    @GetMapping("/orders")
    public List<Order> findByOrders(){
        return service.findByAllOrder();
    }


}
