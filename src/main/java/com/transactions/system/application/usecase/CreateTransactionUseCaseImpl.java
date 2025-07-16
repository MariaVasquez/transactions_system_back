package com.transactions.system.application.usecase;

import com.transactions.system.application.mapper.TransactionMapper;
import com.transactions.system.application.usecase.interfaces.CreateTransactionUseCase;
import com.transactions.system.domain.model.TransactionStatus;
import com.transactions.system.domain.model.Transaction;
import com.transactions.system.domain.port.TransactionRepository;
import com.transactions.system.infraestructure.cache.TransactionCacheCleaner;
import com.transactions.system.infraestructure.web.dto.request.TransactionRequestDto;
import com.transactions.system.infraestructure.web.dto.response.TransactionResponseDto;
import com.transactions.system.shared.constants.ResponseCode;
import com.transactions.system.shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CreateTransactionUseCaseImpl implements CreateTransactionUseCase {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper mapper;
    private final TransactionCacheCleaner transactionCacheCleaner;

    @Override
    public Mono<TransactionResponseDto> execute(TransactionRequestDto transactionRequestDto) {
        log.info("Init save transaction {}", transactionRequestDto.getName());
        return validateStatus(transactionRequestDto.getTransactionStatus())
                .doOnSuccess(status -> log.info("validateStatus passed for status: {}", transactionRequestDto.getTransactionStatus()))
                .then(validateExistsTransaction(transactionRequestDto.getName())
                        .doOnSuccess(exists -> log.info("validateExistsTransaction passed for name: {}", transactionRequestDto.getName()))
                )
                .then(Mono.defer(() -> {
                    Transaction transaction = mapper.toModel(transactionRequestDto);

                    return transactionRepository.save(transaction)
                            .doOnNext(saved -> log.info("Transaction saved: {}", saved))

                            .flatMap(saved ->
                                    transactionCacheCleaner.deleteAllTransactionPages()
                                            .doOnSuccess(v -> log.info("Cache cleared"))
                                            .thenReturn(saved)
                            );
                }))
                .map(mapper::toDto)
                .doOnSuccess(dto -> log.info("Final DTO: {}", dto))
                .doOnError(e -> log.error("Error in save transaction {}", transactionRequestDto.getName(), e))
                .onErrorMap(e -> (e instanceof CustomException) ? e : new CustomException(ResponseCode.DATABASE_ERROR));
    }

    private Mono<Void> validateStatus(TransactionStatus transactionStatus){
        if (transactionStatus != TransactionStatus.PENDIENTE) {
            return Mono.error(new CustomException(ResponseCode.INVALID_TRANSACTION_STATUS));
        }
        return Mono.empty();
    }

    private Mono<Void> validateExistsTransaction(String name) {
        return transactionRepository.findByName(name)
                .hasElement()
                .flatMap(exist -> !Boolean.FALSE.equals(exist)
                        ? Mono.error(new CustomException(ResponseCode.TRANSACTION_EXIST))
                        : Mono.empty()
                );
    }
}
