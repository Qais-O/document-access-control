package qais.omari.document_access_control_system.exception;

import org.springframework.http.HttpStatus;
import qais.omari.document_access_control_system.constants.MessageKeys;

public class AccessDeniedException extends SecurityException {

    public AccessDeniedException(Object... messageArgs) {
        super(MessageKeys.Subcode.ACCESS_DENIED, MessageKeys.Error.ACCESS_DENIED, HttpStatus.FORBIDDEN, messageArgs);
    }

    public AccessDeniedException(Throwable cause, Object... messageArgs) {
        super(MessageKeys.Subcode.ACCESS_DENIED, MessageKeys.Error.ACCESS_DENIED, HttpStatus.FORBIDDEN, cause, messageArgs);
    }
}