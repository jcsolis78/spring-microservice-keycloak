package com.hbt.os.api.models.service;

import com.hbt.os.api.models.commons.TransactionRequest;
import com.hbt.os.api.models.commons.TransactionResponse;
import com.hbt.os.api.models.entity.Order;

import java.util.List;

public interface IOrderService {

    TransactionResponse save(TransactionRequest request) throws Exception;
    List<Order> findByAllOrder();

}
