package com.transactions.system.unit.usecase;

import com.transactions.system.application.mapper.TransactionMapper;
import com.transactions.system.application.usecase.CreateTransactionUseCaseImpl;
import com.transactions.system.domain.model.TransactionStatus;
import com.transactions.system.domain.port.TransactionRepository;
import com.transactions.system.infraestructure.cache.TransactionCacheCleaner;
import com.transactions.system.infraestructure.web.dto.request.TransactionRequestDto;
import com.transactions.system.shared.constants.ResponseCode;
import com.transactions.system.shared.exception.CustomException;
import com.transactions.system.unit.util.DataMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateTransactionUseCaseImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionCacheCleaner transactionCacheCleaner;

    @Mock
    private TransactionMapper mapper;

    private CreateTransactionUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateTransactionUseCaseImpl(transactionRepository, mapper, transactionCacheCleaner);
    }

    @Test
    void shouldCreateTransactionWhenValidRequest() {
        when(transactionRepository.findByName(DataMock.requestData().getName()))
                .thenReturn(Mono.empty());
        when(mapper.toModel(DataMock.requestData()))
                .thenReturn(DataMock.modelData());
        when(transactionRepository.save(DataMock.modelData()))
                .thenReturn(Mono.just(DataMock.modelData()));
        when(transactionCacheCleaner.deleteAllTransactionPages())
                .thenReturn(Mono.empty());
        when(mapper.toDto(DataMock.modelData())).thenReturn(DataMock.responseData());

        StepVerifier.create(useCase.execute(DataMock.requestData()))
                .expectNextMatches(r ->  r.getName().equals("Compra"))
                .verifyComplete();
        verify(transactionCacheCleaner).deleteAllTransactionPages();
    }

    @Test
    void shouldErrorCreateTransactionWhenExistTransaction() {
        when(transactionRepository.findByName(DataMock.requestData().getName()))
                .thenReturn(Mono.just(DataMock.modelData()));

        StepVerifier.create(useCase.execute(DataMock.requestData()))
                .expectErrorMatches(throwable ->
                        throwable instanceof CustomException &&
                                ((CustomException) throwable).getResponseCode().equals(ResponseCode.TRANSACTION_NOT_FOUND)
                )
                .verify();
    }
    @Test
    void shouldErrorCreateTransactionWhenCreateWithDifferentStatus() {
        TransactionRequestDto request = DataMock.requestData();
        request.setTransactionStatus(TransactionStatus.PAGADO);

        when(transactionRepository.findByName(request.getName()))
                .thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(request))
                .expectErrorMatches(throwable ->
                        throwable instanceof CustomException &&
                                ((CustomException) throwable).getResponseCode().equals(ResponseCode.INVALID_TRANSACTION_STATUS)
                )
                .verify();
    }

    @Test
    void shouldErrorCreateTransactionWhenDatabaseReturnException() {

        when(transactionRepository.findByName(DataMock.requestData().getName()))
                .thenReturn(Mono.empty());
        when(mapper.toModel(DataMock.requestData()))
                .thenReturn(DataMock.modelData());
        when(transactionRepository.save(DataMock.modelData()))
                .thenReturn(Mono.error(new RuntimeException("Fallo inesperado")));

        StepVerifier.create(useCase.execute(DataMock.requestData()))
                .expectErrorMatches(error ->
                        error instanceof CustomException &&
                                ((CustomException) error).getResponseCode().equals(ResponseCode.DATABASE_ERROR)
                )
                .verify();
    }
}
