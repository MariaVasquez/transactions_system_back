package com.transactions.system.unit.util;

import com.transactions.system.domain.model.TransactionStatus;
import com.transactions.system.domain.model.Transaction;
import com.transactions.system.infraestructure.web.dto.request.TransactionRequestDto;
import com.transactions.system.infraestructure.web.dto.response.TransactionResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DataMock {

    public static TransactionRequestDto requestData(){
        return TransactionRequestDto
                .builder()
                .name("Compra")
                .transactionDate(LocalDate.now())
                .amount(new BigDecimal("100.00"))
                .transactionStatus(TransactionStatus.PENDIENTE)
                .build();
    }

    public static Transaction modelData(){
        return Transaction
                .builder()
                .name("Compra")
                .transactionDate(LocalDate.now())
                .amount(new BigDecimal("100.00"))
                .transactionStatus(TransactionStatus.PENDIENTE)
                .build();
    }

    public static TransactionResponseDto responseData(){
        return TransactionResponseDto
                .builder()
                .name("Compra")
                .transactionDate(LocalDate.now())
                .amount(new BigDecimal("100.00"))
                .transactionStatus(TransactionStatus.PENDIENTE)
                .build();
    }
}
