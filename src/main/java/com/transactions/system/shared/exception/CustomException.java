package com.transactions.system.shared.exception;


import com.transactions.system.shared.constants.ResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 3508567824775716466L;

    private final ResponseCode responseCode;

    private final List<FieldError> fieldErrors;

    public CustomException(ResponseCode responseCode, String... params) {
        super(MessageFormat.format(responseCode.getMessage(), (Object[]) params));
        this.responseCode = responseCode;
        this.fieldErrors = new ArrayList<>();
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.valueOf(responseCode.getHttpStatus());
    }
}
