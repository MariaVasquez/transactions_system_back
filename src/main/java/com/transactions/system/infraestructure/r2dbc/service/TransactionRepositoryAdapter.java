package com.transactions.system.infraestructure.r2dbc.service;

import com.transactions.system.application.mapper.TransactionMapper;
import com.transactions.system.domain.model.Transaction;
import com.transactions.system.domain.port.TransactionRepository;
import com.transactions.system.infraestructure.r2dbc.repository.TransactionReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepository {
    private final TransactionMapper mapper;
    private final TransactionReactiveRepository transactionReactiveRepository;

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        return null;
    }
}
