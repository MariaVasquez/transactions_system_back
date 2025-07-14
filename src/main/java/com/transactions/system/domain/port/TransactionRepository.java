package com.transactions.system.domain.port;

import com.transactions.system.domain.model.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
    Mono<Transaction> save(Transaction transaction);
}
