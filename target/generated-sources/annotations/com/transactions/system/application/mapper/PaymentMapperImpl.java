package com.transactions.system.application.mapper;

import com.transactions.system.domain.model.Payment;
import com.transactions.system.infraestructure.r2dbc.entity.PaymentEntity;
import com.transactions.system.infraestructure.web.dto.response.PaymentResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-16T00:52:22-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentResponseDto toDto(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentResponseDto.PaymentResponseDtoBuilder paymentResponseDto = PaymentResponseDto.builder();

        paymentResponseDto.id( payment.getId() );
        List<String> list = payment.getTransactionIds();
        if ( list != null ) {
            paymentResponseDto.transactionIds( new ArrayList<String>( list ) );
        }
        paymentResponseDto.amount( payment.getAmount() );
        paymentResponseDto.status( payment.getStatus() );
        paymentResponseDto.createdDate( payment.getCreatedDate() );
        paymentResponseDto.updatedDate( payment.getUpdatedDate() );

        return paymentResponseDto.build();
    }

    @Override
    public PaymentEntity toEntity(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentEntity.PaymentEntityBuilder paymentEntity = PaymentEntity.builder();

        paymentEntity.id( payment.getId() );
        List<String> list = payment.getTransactionIds();
        if ( list != null ) {
            paymentEntity.transactionIds( new ArrayList<String>( list ) );
        }
        paymentEntity.amount( payment.getAmount() );
        paymentEntity.status( payment.getStatus() );
        paymentEntity.createdDate( payment.getCreatedDate() );
        paymentEntity.updatedDate( payment.getUpdatedDate() );

        return paymentEntity.build();
    }

    @Override
    public Payment toDomain(PaymentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Payment.PaymentBuilder payment = Payment.builder();

        payment.id( entity.getId() );
        List<String> list = entity.getTransactionIds();
        if ( list != null ) {
            payment.transactionIds( new ArrayList<String>( list ) );
        }
        payment.amount( entity.getAmount() );
        payment.status( entity.getStatus() );
        payment.createdDate( entity.getCreatedDate() );
        payment.updatedDate( entity.getUpdatedDate() );

        return payment.build();
    }
}
