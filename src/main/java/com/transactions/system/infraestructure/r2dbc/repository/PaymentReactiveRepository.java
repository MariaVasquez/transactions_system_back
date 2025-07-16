package com.transactions.system.infraestructure.r2dbc.repository;

import com.transactions.system.infraestructure.r2dbc.entity.PaymentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PaymentReactiveRepository extends ReactiveMongoRepository<PaymentEntity, String> {
}
