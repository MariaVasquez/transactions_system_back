package com.transactions.system.domain.port;

import com.fasterxml.jackson.core.type.TypeReference;
import reactor.core.publisher.Mono;

public interface RedisCacheService {
    <T> Mono<T> get(String key, TypeReference<T> typeReference);
    <T> Mono<Void> set(String key, T value);
    Mono<Boolean> delete(String key);
}
