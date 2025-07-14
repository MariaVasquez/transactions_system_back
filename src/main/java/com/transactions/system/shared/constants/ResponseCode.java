package com.transactions.system.shared.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    DATABASE_ERROR("TRAPP_ERR_1",500,"Database error"),
    FRANCHISE_NOT_FOUND("TRAPP_ERR_2",404,"Franchise not found"),
    FRANCHISE_EXIST("TRAPP_ERR_3",409,"Franchise {0} already exist"),
    BRANCH_EXIST("TRAPP_ERR_3",409,"Branch {0} already exist"),
    PRODUCT_EXIST("TRAPP_ERR_4",409,"Product {0} already exist"),
    PRODUCT_NOT_FOUND("TRAPP_ERR_5",404,"Product not found"),
    BRANCH_NOT_FOUND("TRAPP_ERR_6",404,"Branch not found"),
    TRANSACTION_SUCCESS("TRAPP_SUCC_1",200, "Ok"),
    TRANSACTION_CREATED("TRAPP_SUCC_1",201, "Created"),
    UNEXPECTED_ERROR("TRAPP_ERR_7",500,"Unexpected error");

    private final String code;
    private final int httpStatus;
    private final String message;

    public String formatMessage(Object... args){return String.format(this.message, args);}

}
