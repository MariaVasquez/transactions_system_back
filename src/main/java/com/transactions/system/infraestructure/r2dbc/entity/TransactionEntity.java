package com.transactions.system.infraestructure.r2dbc.entity;

import com.transactions.system.domain.model.TransactionStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "transactions")
public class TransactionEntity {

    @Id
    private String id;

    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate transactionDate;

    private BigDecimal amount;

    private TransactionStatus transactionStatus;

    private String paymentId;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

}