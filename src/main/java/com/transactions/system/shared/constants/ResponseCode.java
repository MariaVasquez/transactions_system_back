package com.transactions.system.shared.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    DATABASE_ERROR("TRAPP_ERR_1",500,"Database error"),
    TRANSACTION_NOT_FOUND("TRAPP_ERR_2",404,"Transaction not found"),
    TRANSACTIONS_EMPTY("TRAPP_ERR_3",400,"Transactions empty"),
    TRANSACTION_EXIST("TRAPP_ERR_3",400,"Transactions is exist"),
    TRANSACTION_SUCCESS("TRAPP_SUCC_1",200, "Ok"),
    TRANSACTION_CREATED("TRAPP_SUCC_1",201, "Created"),
    UNEXPECTED_ERROR("TRAPP_ERR_4",500,"Unexpected error"),
    PAYMENT_AMOUNT_TOO_LOW("TRAPP_ERR_5",400,"El monto del pago no es suficiente para cubrir ninguna de las transacciones pendientes."),
    INVALID_TRANSACTION_STATUS("TRAPP_ERR_6", 400, "Invalid transaction status. Only 'PENDIENTE' is allowed when creating.");

    private final String code;
    private final int httpStatus;
    private final String message;

    public String formatMessage(Object... args){return String.format(this.message, args);}

}
