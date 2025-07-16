package com.transactions.system.infraestructure.cache;

import com.transactions.system.domain.port.RedisCacheService;
import com.transactions.system.domain.port.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionCacheCleaner {

    @Value("${TRANSACTION_PAGE_CACHE_KEY:transactions::page:%d_size:%d}")
    private String cacheKeyFormat;

    private final RedisCacheService redisCacheService;
    private final TransactionRepository transactionRepository;

    public Mono<Void> deleteAllTransactionPages() {
        int pageSize = 10;

        return transactionRepository.getTotalTransaction()
                .defaultIfEmpty(0L)
                .flatMapMany(total -> {
                    int totalPages = (int) Math.ceil((double) total / pageSize);
                    List<String> keys = IntStream.range(0, totalPages)
                            .mapToObj(page -> String.format(cacheKeyFormat, page, pageSize))
                            .toList();
                    return Flux.fromIterable(keys);
                })
                .flatMap(redisCacheService::delete)
                .doOnNext(deleted -> log.info("¿Clave eliminada? {}", deleted))
                .then();
    }
}
