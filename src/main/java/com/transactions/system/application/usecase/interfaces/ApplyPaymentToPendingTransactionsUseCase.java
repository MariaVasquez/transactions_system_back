package com.transactions.system.application.usecase.interfaces;

import com.transactions.system.infraestructure.web.dto.request.PaymentRequestDto;
import com.transactions.system.infraestructure.web.dto.response.PaymentResponseDto;
import reactor.core.publisher.Mono;

public interface ApplyPaymentToPendingTransactionsUseCase {
    Mono<PaymentResponseDto> execute(PaymentRequestDto requestDto);
}
