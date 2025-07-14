package com.transactions.system.infraestructure.web.dto.response;


import com.transactions.system.domain.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Schema(description = "Respuesta con los datos de una transacción")
public class TransactionResponse {
    @Schema(description = "Identificador único de la transacción", example = "64a6e8c5b0d0f81024b9c001")
    private String id;

    @Schema(description = "Nombre de la transacción", example = "Pago luz julio")
    private String name;

    @Schema(description = "Fecha de la transacción", example = "2025-07-09")
    private LocalDate transactionDate;

    @Schema(description = "Monto involucrado", example = "90000.00")
    private BigDecimal amount;

    @Schema(description = "Estado actual de la transacción", example = "PENDIENTE")
    private Status status;

    @Schema(description = "Fecha de creación del registro", example = "2025-07-09T15:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de la última actualización", example = "2025-07-09T15:40:00")
    private LocalDateTime updatedAt;
}
