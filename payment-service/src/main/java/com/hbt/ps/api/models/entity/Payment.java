package com.hbt.ps.api.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "hbt_payment")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {

    private static final long serialVersionUID = -4351711101907510128L;

    @Id
    @GeneratedValue
    private Long paymentId;
    private String paymentStatus;
    private String transactionId;
    private int orderId;
    private Double amount;



}

