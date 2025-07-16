package com.transactions.system.infraestructure.r2dbc.entity;

import com.transactions.system.domain.model.PaymentStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "payments")
public class PaymentEntity {
    @Id
    private String id;
    private List<String> transactionIds;
    private BigDecimal amount;
    private PaymentStatus status;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;
}
