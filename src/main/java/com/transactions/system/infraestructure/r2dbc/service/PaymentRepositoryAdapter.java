package com.transactions.system.infraestructure.r2dbc.service;

import com.transactions.system.application.mapper.PaymentMapper;
import com.transactions.system.domain.model.Payment;
import com.transactions.system.domain.port.PaymentRepository;
import com.transactions.system.infraestructure.r2dbc.repository.PaymentReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepository {
    private final PaymentMapper mapper;
    private final PaymentReactiveRepository paymentReactiveRepository;

    @Override
    public Mono<Payment> save(Payment payment) {
        return paymentReactiveRepository.save(mapper.toEntity(payment))
                .map(mapper::toDomain);
    }
}
