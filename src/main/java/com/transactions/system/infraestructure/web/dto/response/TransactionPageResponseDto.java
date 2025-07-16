package com.transactions.system.infraestructure.web.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TransactionPageResponseDto {
    private int page;
    private int size;
    private long total;
    private List<TransactionResponseDto> transactions;
}
