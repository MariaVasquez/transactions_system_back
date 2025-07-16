package com.transactions.system.infraestructure.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {
    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    @Schema(description = "Monto del pago", example = "150000.00", required = true)
    private BigDecimal amount;
}
