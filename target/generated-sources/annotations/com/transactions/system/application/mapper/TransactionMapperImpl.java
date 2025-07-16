package com.transactions.system.application.mapper;

import com.transactions.system.domain.model.Transaction;
import com.transactions.system.infraestructure.r2dbc.entity.TransactionEntity;
import com.transactions.system.infraestructure.web.dto.request.TransactionRequestDto;
import com.transactions.system.infraestructure.web.dto.response.TransactionResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-16T00:52:22-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction toModel(TransactionRequestDto request) {
        if ( request == null ) {
            return null;
        }

        Transaction.TransactionBuilder transaction = Transaction.builder();

        transaction.name( request.getName() );
        transaction.amount( request.getAmount() );
        transaction.transactionDate( request.getTransactionDate() );
        transaction.transactionStatus( request.getTransactionStatus() );

        return transaction.build();
    }

    @Override
    public TransactionResponseDto toDto(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionResponseDto.TransactionResponseDtoBuilder transactionResponseDto = TransactionResponseDto.builder();

        transactionResponseDto.id( transaction.getId() );
        transactionResponseDto.name( transaction.getName() );
        transactionResponseDto.transactionDate( transaction.getTransactionDate() );
        transactionResponseDto.amount( transaction.getAmount() );
        transactionResponseDto.transactionStatus( transaction.getTransactionStatus() );
        transactionResponseDto.createdDate( transaction.getCreatedDate() );
        transactionResponseDto.updatedDate( transaction.getUpdatedDate() );

        return transactionResponseDto.build();
    }

    @Override
    public TransactionEntity toEntity(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionEntity.TransactionEntityBuilder transactionEntity = TransactionEntity.builder();

        transactionEntity.id( transaction.getId() );
        transactionEntity.name( transaction.getName() );
        transactionEntity.transactionDate( transaction.getTransactionDate() );
        transactionEntity.amount( transaction.getAmount() );
        transactionEntity.transactionStatus( transaction.getTransactionStatus() );
        transactionEntity.createdDate( transaction.getCreatedDate() );
        transactionEntity.updatedDate( transaction.getUpdatedDate() );

        return transactionEntity.build();
    }

    @Override
    public Transaction toDomain(TransactionEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Transaction.TransactionBuilder transaction = Transaction.builder();

        transaction.id( entity.getId() );
        transaction.name( entity.getName() );
        transaction.amount( entity.getAmount() );
        transaction.transactionDate( entity.getTransactionDate() );
        transaction.transactionStatus( entity.getTransactionStatus() );
        transaction.createdDate( entity.getCreatedDate() );
        transaction.updatedDate( entity.getUpdatedDate() );

        return transaction.build();
    }
}
