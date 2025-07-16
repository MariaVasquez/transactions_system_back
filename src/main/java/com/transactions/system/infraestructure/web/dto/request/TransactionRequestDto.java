package com.transactions.system.infraestructure.web.dto.request;

import com.transactions.system.domain.model.TransactionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Schema(description = "DTO para registrar o actualizar una transacción")
public class TransactionRequestDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(description = "Nombre de la transacción", example = "Compra supermercado")
    private String name;

    @NotNull(message = "La fecha de la transacción es obligatoria")
    @Schema(description = "Fecha en la que ocurrió la transacción", example = "2025-07-09")
    private LocalDate transactionDate;

    @NotNull(message = "El valor es obligatorio")
    @DecimalMin(value = "0.01", message = "El valor debe ser mayor a cero")
    @Schema(description = "Monto de la transacción", example = "150000.00")
    private BigDecimal amount;

    @NotNull(message = "El estado es obligatorio")
    @Schema(description = "Estado de la transacción", example = "PENDIENTE")
    private TransactionStatus transactionStatus;
}
