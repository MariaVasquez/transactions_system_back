package com.transactions.system.application.usecase.interfaces;

import com.transactions.system.infraestructure.web.dto.request.TransactionRequestDto;
import com.transactions.system.infraestructure.web.dto.response.TransactionResponseDto;
import reactor.core.publisher.Mono;

public interface CreateTransactionUseCase {
    Mono<TransactionResponseDto> execute(TransactionRequestDto transactionRequestDto);

}
