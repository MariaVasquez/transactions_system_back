package com.transactions.system.infraestructure.web.controller;

import com.transactions.system.application.usecase.interfaces.CreateTransactionUseCase;
import com.transactions.system.application.usecase.interfaces.GetAllTransactionUseCase;
import com.transactions.system.infraestructure.web.dto.request.TransactionRequestDto;
import com.transactions.system.infraestructure.web.dto.response.TransactionPageResponseDto;
import com.transactions.system.infraestructure.web.dto.response.TransactionResponseDto;
import com.transactions.system.shared.constants.ResponseCode;
import com.transactions.system.shared.dto.GenericResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
@Tag(name = "Transacciones", description = "API para gestionar transacciones")
public class TransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;
    private final GetAllTransactionUseCase getAllTransactionUseCase;

    @PostMapping
    @Operation(
            summary = "Crear transacción",
            description = "Permite crear una nueva transacción",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Transacción creada",
                            content = @Content(schema = @Schema(implementation = TransactionResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos",
                            content = @Content)
            }
    )
    public Mono<GenericResponseDto<TransactionResponseDto>> createFranchise(
            @org.springframework.web.bind.annotation.RequestBody
            @Valid
            @RequestBody(
                    description = "Datos necesarios para registrar una franquicia",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TransactionRequestDto.class))
            )
            TransactionRequestDto requestDto
    ) {
        return createTransactionUseCase.execute(requestDto)
                .map(data -> new GenericResponseDto<>(ResponseCode.TRANSACTION_CREATED, data));
    }

    @GetMapping
    @Operation(
            summary = "Listar transacciones",
            description = "Obtiene una lista paginada de transacciones",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transacciones obtenidas exitosamente",
                            content = @Content(schema = @Schema(implementation = TransactionPageResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Parámetros inválidos",
                            content = @Content)
            }
    )
    public Mono<GenericResponseDto<TransactionPageResponseDto>> getAllTransactions(
            @Parameter(description = "Página actual", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de la página", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        return getAllTransactionUseCase.execute(page, size)
                .map(data -> new GenericResponseDto<>(ResponseCode.TRANSACTION_SUCCESS, data));
    }
}
