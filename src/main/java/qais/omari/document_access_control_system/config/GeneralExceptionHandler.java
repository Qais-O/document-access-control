package qais.omari.document_access_control_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import qais.omari.document_access_control_system.constants.ErrorCodes;
import qais.omari.document_access_control_system.constants.MessageKeys;
import qais.omari.document_access_control_system.constants.ResponseConstants;
import qais.omari.document_access_control_system.exception.BaseException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GeneralExceptionHandler {

    @Autowired
    private MessageService messageService;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Map<String, Object>> handleBaseException(
            BaseException ex, WebRequest request) {

        String acceptLanguage = request.getHeader(ResponseConstants.Headers.ACCEPT_LANGUAGE);
        String localizedMessage = messageService.getMessage(ex.getMessageKey(), acceptLanguage, ex.getMessageArgs());

        Map<String, Object> body = new HashMap<>();
        body.put(ResponseConstants.Fields.TIMESTAMP, LocalDateTime.now());
        body.put(ResponseConstants.Fields.STATUS, ex.getHttpStatus().value());
        body.put(ResponseConstants.Fields.ERROR, ex.getHttpStatus().getReasonPhrase());
        body.put(ResponseConstants.Fields.ERROR_CODE, ex.getErrorCode());
        body.put(ResponseConstants.Fields.MESSAGE, localizedMessage);
        body.put(ResponseConstants.Fields.PATH, request.getDescription(false).replace(ResponseConstants.Headers.URI_PREFIX, ""));

        return new ResponseEntity<>(body, ex.getHttpStatus());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleSpringSecurityAccessDenied(
            AuthorizationDeniedException ex, WebRequest request) {

        String acceptLanguage = request.getHeader(ResponseConstants.Headers.ACCEPT_LANGUAGE);
        String localizedMessage = messageService.getMessage(MessageKeys.Error.ACCESS_DENIED, acceptLanguage);

        Map<String, Object> body = new HashMap<>();
        body.put(ResponseConstants.Fields.TIMESTAMP, LocalDateTime.now());
        body.put(ResponseConstants.Fields.STATUS, HttpStatus.FORBIDDEN.value());
        body.put(ResponseConstants.Fields.ERROR, ResponseConstants.ErrorTypes.ACCESS_DENIED);
        body.put(ResponseConstants.Fields.ERROR_CODE, ErrorCodes.Security.ACCESS_DENIED);
        body.put(ResponseConstants.Fields.MESSAGE, localizedMessage);
        body.put(ResponseConstants.Fields.PATH, request.getDescription(false).replace(ResponseConstants.Headers.URI_PREFIX, ""));

        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthentication(
            AuthenticationException ex, WebRequest request) {

        String acceptLanguage = request.getHeader(ResponseConstants.Headers.ACCEPT_LANGUAGE);
        String localizedMessage = messageService.getMessage(MessageKeys.Error.AUTHENTICATION_FAILED, acceptLanguage);

        Map<String, Object> body = new HashMap<>();
        body.put(ResponseConstants.Fields.TIMESTAMP, LocalDateTime.now());
        body.put(ResponseConstants.Fields.STATUS, HttpStatus.UNAUTHORIZED.value());
        body.put(ResponseConstants.Fields.ERROR, ResponseConstants.ErrorTypes.AUTHENTICATION_FAILED);
        body.put(ResponseConstants.Fields.ERROR_CODE, ErrorCodes.Security.AUTHENTICATION_FAILED);
        body.put(ResponseConstants.Fields.MESSAGE, localizedMessage);
        body.put(ResponseConstants.Fields.PATH, request.getDescription(false).replace(ResponseConstants.Headers.URI_PREFIX, ""));

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex, WebRequest request) {

        String acceptLanguage = request.getHeader(ResponseConstants.Headers.ACCEPT_LANGUAGE);
        String localizedMessage = messageService.getMessage(MessageKeys.Error.VALIDATION_FAILED, acceptLanguage);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            
            // If the message contains curly braces, it's a message key, resolve it
            if (errorMessage != null && errorMessage.startsWith("{") && errorMessage.endsWith("}")) {
                String messageKey = errorMessage.substring(1, errorMessage.length() - 1);
                errorMessage = messageService.getMessage(messageKey, acceptLanguage);
            }
            
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> body = new HashMap<>();
        body.put(ResponseConstants.Fields.TIMESTAMP, LocalDateTime.now());
        body.put(ResponseConstants.Fields.STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(ResponseConstants.Fields.ERROR, ResponseConstants.ErrorTypes.VALIDATION_FAILED);
        body.put(ResponseConstants.Fields.ERROR_CODE, ErrorCodes.Business.VALIDATION);
        body.put(ResponseConstants.Fields.MESSAGE, localizedMessage);
        body.put(ResponseConstants.Fields.ERRORS, errors);
        body.put(ResponseConstants.Fields.PATH, request.getDescription(false).replace(ResponseConstants.Headers.URI_PREFIX, ""));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(
            Exception ex, WebRequest request) {

        String acceptLanguage = request.getHeader(ResponseConstants.Headers.ACCEPT_LANGUAGE);
        String localizedMessage = messageService.getMessage(MessageKeys.Error.INTERNAL_SERVER, acceptLanguage);

        Map<String, Object> body = new HashMap<>();
        body.put(ResponseConstants.Fields.TIMESTAMP, LocalDateTime.now());
        body.put(ResponseConstants.Fields.STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put(ResponseConstants.Fields.ERROR, ResponseConstants.ErrorTypes.INTERNAL_SERVER_ERROR);
        body.put(ResponseConstants.Fields.ERROR_CODE, ErrorCodes.Technical.INTERNAL_SERVER);
        body.put(ResponseConstants.Fields.MESSAGE, localizedMessage);
        body.put(ResponseConstants.Fields.PATH, request.getDescription(false).replace(ResponseConstants.Headers.URI_PREFIX, ""));

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}