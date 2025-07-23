package qais.omari.document_access_control_system.exception;

import org.springframework.http.HttpStatus;
import qais.omari.document_access_control_system.constants.MessageKeys;

public class UnauthorizedException extends SecurityException {

    public UnauthorizedException(Object... messageArgs) {
        super(MessageKeys.Subcode.UNAUTHORIZED, MessageKeys.Error.UNAUTHORIZED, HttpStatus.UNAUTHORIZED, messageArgs);
    }

    public UnauthorizedException(Throwable cause, Object... messageArgs) {
        super(MessageKeys.Subcode.UNAUTHORIZED, MessageKeys.Error.UNAUTHORIZED, HttpStatus.UNAUTHORIZED, cause, messageArgs);
    }
}