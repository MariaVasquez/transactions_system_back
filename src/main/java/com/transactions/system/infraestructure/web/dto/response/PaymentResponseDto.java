package com.transactions.system.infraestructure.web.dto.response;

import com.transactions.system.domain.model.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {
    @Schema(description = "ID del pago", example = "pay_001")
    private String id;

    @Schema(description = "ID de la transacción asociada", example = "660f4e48c15b9f3b8e02c571")
    private List<String> transactionIds;

    @Schema(description = "Monto del pago", example = "150000.00")
    private BigDecimal amount;

    @Schema(description = "Estado del pago", example = "SUCCESS")
    private PaymentStatus status;

    @Schema(description = "Fecha de creación del pago", example = "2025-07-15T21:40:00")
    private LocalDateTime createdDate;

    @Schema(description = "Fecha de última actualización del pago", example = "2025-07-15T21:55:00")
    private LocalDateTime updatedDate;

    @Schema(description = "Monto sobrante", example = "100.00")
    private BigDecimal remainingAmount;
}
