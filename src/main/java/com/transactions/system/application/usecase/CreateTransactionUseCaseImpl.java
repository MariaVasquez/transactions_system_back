package com.transactions.system.application.usecase;

import com.transactions.system.application.usecase.interfaces.CreateTransactionUseCase;
import com.transactions.system.domain.port.TransactionRepository;
import com.transactions.system.infraestructure.web.dto.request.TransactionRequest;
import com.transactions.system.infraestructure.web.dto.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateTransactionUseCaseImpl implements CreateTransactionUseCase {
    private final TransactionRepository transactionRepository;
    @Override
    public Mono<TransactionResponse> execute(TransactionRequest transactionRequest) {
        return null;
    }
}
