package com.transactions.system.application.mapper;

import com.transactions.system.domain.model.Payment;
import com.transactions.system.infraestructure.r2dbc.entity.PaymentEntity;
import com.transactions.system.infraestructure.web.dto.response.PaymentResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponseDto toDto(Payment payment);
    PaymentEntity toEntity(Payment payment);
    Payment toDomain(PaymentEntity entity);
}
