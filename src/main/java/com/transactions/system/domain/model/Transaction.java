package com.transactions.system.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
public class Transaction {
    private String id;
    private String name;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private TransactionStatus transactionStatus;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
