package com.transactions.system.application.usecase.interfaces;

import com.transactions.system.infraestructure.web.dto.request.TransactionRequest;
import com.transactions.system.infraestructure.web.dto.response.TransactionResponse;
import reactor.core.publisher.Mono;

public interface CreateTransactionUseCase {
    Mono<TransactionResponse> execute(TransactionRequest transactionRequest);

}
