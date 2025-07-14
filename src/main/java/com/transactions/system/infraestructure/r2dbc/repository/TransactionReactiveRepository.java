package com.transactions.system.infraestructure.r2dbc.repository;

import com.transactions.system.infraestructure.r2dbc.entity.TransactionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionReactiveRepository extends ReactiveMongoRepository<TransactionEntity, String> {
}
