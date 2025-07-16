package com.transactions.system.application.usecase;

import com.transactions.system.application.mapper.PaymentMapper;
import com.transactions.system.application.usecase.interfaces.ApplyPaymentToPendingTransactionsUseCase;
import com.transactions.system.domain.model.Payment;
import com.transactions.system.domain.model.PaymentStatus;
import com.transactions.system.domain.model.Transaction;
import com.transactions.system.domain.model.TransactionStatus;
import com.transactions.system.domain.port.PaymentRepository;
import com.transactions.system.domain.port.TransactionRepository;
import com.transactions.system.infraestructure.cache.TransactionCacheCleaner;
import com.transactions.system.infraestructure.web.dto.request.PaymentRequestDto;
import com.transactions.system.infraestructure.web.dto.response.PaymentResponseDto;
import com.transactions.system.shared.constants.ResponseCode;
import com.transactions.system.shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ApplyPaymentToPendingTransactionsUseCaseImpl implements ApplyPaymentToPendingTransactionsUseCase {
    private final PaymentRepository paymentRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionCacheCleaner transactionCacheCleaner;
    private final PaymentMapper mapper;

    @Override
    public Mono<PaymentResponseDto> execute(PaymentRequestDto requestDto) {
        return validateExistTransaction()
                .collectList()
                .flatMap(transactions -> {
                    List<Transaction> transactionsToPay = accruePayableTransactions(transactions, requestDto.getAmount());
                    if (transactionsToPay.isEmpty()) {
                        return Mono.error(new CustomException(ResponseCode.PAYMENT_AMOUNT_TOO_LOW));
                    }
                    return updateTransactionStatus(transactionsToPay);
                })
                .flatMap(transactions -> {
                    Payment payment = buildPayment(transactions, requestDto.getAmount());
                    return paymentRepository.save(payment)
                            .doOnNext(saved -> log.info("Payment saved: {}", saved))
                            .flatMap(saved ->
                                    transactionCacheCleaner.deleteAllTransactionPages()
                                            .doOnSuccess(v -> log.info("Cache cleared"))
                                            .thenReturn(Tuples.of(saved, transactions))
                            );
                })
                .map(tuple -> {
                    Payment payment = tuple.getT1();
                    List<Transaction> transactions = tuple.getT2();

                    PaymentResponseDto dto = mapper.toDto(payment);

                    BigDecimal totalPaid = transactions.stream()
                            .map(Transaction::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal remainingAmount = calculateRemainingAmount(payment.getAmount(), totalPaid);
                    dto.setRemainingAmount(remainingAmount);
                    return dto;
                })
                .doOnSuccess(dto -> log.info("Final DTO: {}", dto))
                .doOnError(e -> log.error("Error in save payment ", e))
                .onErrorMap(e -> (e instanceof CustomException) ? e : new CustomException(ResponseCode.DATABASE_ERROR));

    }

    private Flux<Transaction> validateExistTransaction() {
        return transactionRepository.findAllByStatusCreateDateDesc(TransactionStatus.PENDIENTE)
                .switchIfEmpty(Mono.error(new CustomException(ResponseCode.TRANSACTIONS_EMPTY)));
    }

    private List<Transaction> accruePayableTransactions(List<Transaction> transactions, BigDecimal amountAvailable) {
        BigDecimal runningTotal = BigDecimal.ZERO;
        List<Transaction> transactionsToPay = new ArrayList<>();
        for (Transaction transaction : transactions) {
            BigDecimal nextTotal = runningTotal.add(transaction.getAmount());
            if (nextTotal.compareTo(amountAvailable) <= 0) {
                transactionsToPay.add(transaction);
                runningTotal = nextTotal;
            } else {
                break;
            }
        }
        return transactionsToPay;
    }

    private Mono<List<Transaction>> updateTransactionStatus(List<Transaction> transactions) {
        return Flux.fromIterable(transactions)
                .flatMap(t -> {
                    log.info("Updating transaction: {}", t.getId());
                    return transactionRepository.findById(t.getId())
                            .switchIfEmpty(Mono.error(new CustomException(ResponseCode.TRANSACTION_NOT_FOUND)))
                            .flatMap(transaction -> {
                                transaction.setTransactionStatus(TransactionStatus.PAGADO);
                                return transactionRepository.save(transaction);
                            })
                            .doOnSuccess(transaction -> log.info("Transaction saved with new status: {}", transaction.getTransactionStatus()));
                })
                .doOnComplete(() -> log.info("All transactions updated"))
                .collectList()
                .doOnSuccess(list -> log.info("Collected {} updated transactions", list.size()))
                .doOnError(e -> log.error("Error during updateTransactionStatus: {}", e.getMessage()));
    }

    private BigDecimal calculateRemainingAmount(BigDecimal amountAvailable, BigDecimal totalPaid) {
        return amountAvailable.compareTo(totalPaid) > 0
                ? amountAvailable.subtract(totalPaid)
                : BigDecimal.ZERO;
    }

    private Payment buildPayment(List<Transaction> transactions, BigDecimal amountAvailable) {
        List<String> transactionsId = transactions.stream()
                .map(Transaction::getId)
                .toList();

        return Payment
                .builder()
                .amount(amountAvailable)
                .status(PaymentStatus.SUCCESS)
                .transactionIds(transactionsId)
                .build();
    }
}
