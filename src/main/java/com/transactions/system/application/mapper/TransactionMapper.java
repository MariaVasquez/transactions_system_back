package com.transactions.system.application.mapper;

import com.transactions.system.domain.model.Transaction;
import com.transactions.system.infraestructure.r2dbc.entity.TransactionEntity;
import com.transactions.system.infraestructure.web.dto.request.TransactionRequestDto;
import com.transactions.system.infraestructure.web.dto.response.TransactionResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toModel(TransactionRequestDto request);
    TransactionResponseDto toDto(Transaction transaction);
    TransactionEntity toEntity(Transaction transaction);
    Transaction toDomain(TransactionEntity entity);
}
