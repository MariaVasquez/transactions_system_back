package com.transactions.system.infraestructure.r2dbc.repository;

import com.transactions.system.domain.model.TransactionStatus;
import com.transactions.system.infraestructure.r2dbc.entity.TransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface TransactionReactiveRepository extends ReactiveMongoRepository<TransactionEntity, String> {
    Mono<TransactionEntity> findByName(String name);
    Flux<TransactionEntity> findAllBy(Pageable pageable);
    Flux<TransactionEntity> findAllByTransactionStatusOrderByCreatedDateAsc(TransactionStatus status);
}
