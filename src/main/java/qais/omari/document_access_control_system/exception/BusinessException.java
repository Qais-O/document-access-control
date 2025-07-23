package qais.omari.document_access_control_system.exception;

import org.springframework.http.HttpStatus;

public abstract class BusinessException extends BaseException {
    private static final String BASE_CODE = "BUS";

    protected BusinessException(String subcodeKey, String messageKey, HttpStatus httpStatus, Object... messageArgs) {
        super(BASE_CODE, subcodeKey, messageKey, httpStatus, messageArgs);
    }

    protected BusinessException(String subcodeKey, String messageKey, HttpStatus httpStatus, Throwable cause, Object... messageArgs) {
        super(BASE_CODE, subcodeKey, messageKey, httpStatus, cause, messageArgs);
    }
}