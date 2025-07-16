package com.transactions.system.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String id;
    private List<String> transactionIds;
    private BigDecimal amount;
    private BigDecimal remainingAmount;
    private PaymentStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
