package com.transactions.system.domain.port;

import com.transactions.system.domain.model.Transaction;
import com.transactions.system.domain.model.TransactionStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
    Mono<Transaction> save(Transaction transaction);
    Mono<Transaction> findById(String id);
    Mono<Transaction> findByName(String name);
    Mono<Long> getTotalTransaction();
    Flux<Transaction> findAll(int page, int size);
    Flux<Transaction> findAllByStatusCreateDateDesc(TransactionStatus status);
}
