package com.transactions.system.application.usecase.interfaces;

import com.transactions.system.infraestructure.web.dto.response.TransactionPageResponseDto;
import reactor.core.publisher.Mono;

public interface GetAllTransactionUseCase {
    Mono<TransactionPageResponseDto> execute(int page, int size);
}
