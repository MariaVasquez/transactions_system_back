package com.transactions.system.infraestructure.r2dbc.service;

import com.transactions.system.application.mapper.TransactionMapper;
import com.transactions.system.domain.model.Transaction;
import com.transactions.system.domain.model.TransactionStatus;
import com.transactions.system.domain.port.TransactionRepository;
import com.transactions.system.infraestructure.r2dbc.repository.TransactionReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepository {
    private final TransactionMapper mapper;
    private final TransactionReactiveRepository transactionReactiveRepository;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        return transactionReactiveRepository
                .save(mapper.toEntity(transaction))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Transaction> findById(String id) {
        return transactionReactiveRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Transaction> findByName(String name) {
        return transactionReactiveRepository.findByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Long> getTotalTransaction() {
        return transactionReactiveRepository.count();
    }

    @Override
    public Flux<Transaction> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return transactionReactiveRepository.findAllBy(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Transaction> findAllByStatusCreateDateDesc(TransactionStatus status) {
        return transactionReactiveRepository.findAllByTransactionStatusOrderByCreatedDateAsc(status)
                .map(mapper::toDomain);
    }
}
