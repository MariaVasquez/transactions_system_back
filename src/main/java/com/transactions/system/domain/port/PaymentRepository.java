package com.transactions.system.domain.port;

import com.transactions.system.domain.model.Payment;
import reactor.core.publisher.Mono;

public interface PaymentRepository {
    Mono<Payment> save(Payment payment);
}
