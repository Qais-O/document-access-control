package qais.omari.document_access_control_system.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import qais.omari.document_access_control_system.config.MessageService;
import qais.omari.document_access_control_system.context.ApplicationContextProvider;

import java.util.Locale;

@Getter
public abstract class BaseException extends RuntimeException {
    private final String errorCode;
    private final String messageKey;
    private final HttpStatus httpStatus;
    private final Object[] messageArgs;

    protected BaseException(String baseCode, String subcodeKey, String messageKey, HttpStatus httpStatus, Object... messageArgs) {
        super(messageKey);
        this.errorCode = baseCode + "-" + resolveSubcode(subcodeKey);
        this.messageKey = messageKey;
        this.httpStatus = httpStatus;
        this.messageArgs = messageArgs;
    }

    protected BaseException(String baseCode, String subcodeKey, String messageKey, HttpStatus httpStatus, Throwable cause, Object... messageArgs) {
        super(messageKey, cause);
        this.errorCode = baseCode + "-" + resolveSubcode(subcodeKey);
        this.messageKey = messageKey;
        this.httpStatus = httpStatus;
        this.messageArgs = messageArgs;
    }

    private static String resolveSubcode(String subcodeKey) {
        try {
            MessageService messageService = ApplicationContextProvider.getBean(MessageService.class);
            // Always use default locale for subcodes since they should be the same across languages
            return messageService.getMessage(subcodeKey, Locale.getDefault());
        } catch (Exception e) {
            // Fallback to default if message service is not available
            return "001";
        }
    }
}