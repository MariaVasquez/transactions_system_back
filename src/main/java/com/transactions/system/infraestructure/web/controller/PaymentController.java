package com.transactions.system.infraestructure.web.controller;

import com.transactions.system.application.usecase.interfaces.ApplyPaymentToPendingTransactionsUseCase;
import com.transactions.system.infraestructure.web.dto.request.PaymentRequestDto;
import com.transactions.system.infraestructure.web.dto.response.PaymentResponseDto;
import com.transactions.system.shared.constants.ResponseCode;
import com.transactions.system.shared.dto.GenericResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Endpoints relacionados con la gestión de pagos")
public class PaymentController {
    private final ApplyPaymentToPendingTransactionsUseCase paymentUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Realizar un pago",
            description = "Procesa las transacciones pendientes más antiguas hasta completar el monto especificado. " +
                    "Si el monto no cubre ninguna transacción completa, se lanza error.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Pago realizado con éxito",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PaymentResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Pago inválido (ej. monto insuficiente o sin transacciones pendientes)",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    public Mono<GenericResponseDto<PaymentResponseDto>> createPayment(@RequestBody @Valid PaymentRequestDto requestDto) {
        return paymentUseCase.execute(requestDto)
                .map(data -> new GenericResponseDto<>(ResponseCode.TRANSACTION_SUCCESS, data));
    }
}
