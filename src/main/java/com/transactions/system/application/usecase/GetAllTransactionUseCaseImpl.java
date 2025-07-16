package com.transactions.system.application.usecase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.transactions.system.application.mapper.TransactionMapper;
import com.transactions.system.application.usecase.interfaces.GetAllTransactionUseCase;
import com.transactions.system.domain.port.RedisCacheService;
import com.transactions.system.domain.port.TransactionRepository;
import com.transactions.system.infraestructure.web.dto.response.TransactionPageResponseDto;
import com.transactions.system.infraestructure.web.dto.response.TransactionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetAllTransactionUseCaseImpl implements GetAllTransactionUseCase {
    @Value("${TRANSACTION_PAGE_CACHE_KEY:transactions::page:%d_size:%d}")
    private String cacheKeyFormat;

    private final RedisCacheService redisCacheService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper mapper;

    @Override
    public Mono<TransactionPageResponseDto> execute(int page, int size) {
        String cacheKey = buildCacheKey(page, size);

        return redisCacheService.get(cacheKey, new TypeReference<List<TransactionResponseDto>>() {
                })
                .switchIfEmpty(Mono.defer(() -> loadAndCacheTransactions(page, size, cacheKey)))
                .flatMap(transaction -> buildPageResponse(transaction, page, size));
    }

    private String buildCacheKey(int page, int size) {
        return String.format(cacheKeyFormat, page, size);
    }

    private Mono<List<TransactionResponseDto>> loadAndCacheTransactions(int page, int size, String cacheKey) {
        return transactionRepository.findAll(page, size)
                .map(mapper::toDto)
                .collectList()
                .flatMap(transactions ->
                        redisCacheService.set(cacheKey, transactions)
                                .thenReturn(transactions)
                );
    }

    private Mono<TransactionPageResponseDto> buildPageResponse(List<TransactionResponseDto> transactions, int page, int size) {
        return transactionRepository.getTotalTransaction()
                .map(total -> TransactionPageResponseDto.builder()
                        .transactions(transactions)
                        .page(page)
                        .size(size)
                        .total(total)
                        .build());
    }
}
