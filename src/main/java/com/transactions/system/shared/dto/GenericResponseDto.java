package com.transactions.system.shared.dto;

import com.transactions.system.shared.constants.ResponseCode;
import com.transactions.system.shared.exception.FieldError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class GenericResponseDto<T> {
    private String responseCode;
    private int status;
    private String responseMessage;
    private T data;
    private List<FieldError> fieldErrors;


    public GenericResponseDto(ResponseCode responseCode, T data) {
        this.responseCode = responseCode.toString();
        this.status = responseCode.getHttpStatus();
        this.responseMessage = responseCode.getMessage();
        this.data = data;
        this.fieldErrors = new ArrayList<>();
    }

    public GenericResponseDto(ResponseCode responseCode, String responseMessage, List<FieldError> fieldErrors) {
        this.status = responseCode.getHttpStatus();
        this.responseCode = responseCode.toString();
        this.responseMessage = responseMessage;
        this.fieldErrors = fieldErrors;
    }

    public GenericResponseDto(String message, List<FieldError> fieldErrors) {
        this.responseMessage = message;
        this.fieldErrors = fieldErrors;
    }
}
