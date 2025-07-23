package qais.omari.document_access_control_system.exception;

import org.springframework.http.HttpStatus;
import qais.omari.document_access_control_system.constants.MessageKeys;

public class UserNotFoundException extends SecurityException {

    public UserNotFoundException(Object... messageArgs) {
        super(MessageKeys.Subcode.USER_NOT_FOUND, MessageKeys.Error.USER_NOT_FOUND, HttpStatus.NOT_FOUND, messageArgs);
    }

    public UserNotFoundException(Throwable cause, Object... messageArgs) {
        super(MessageKeys.Subcode.USER_NOT_FOUND, MessageKeys.Error.USER_NOT_FOUND, HttpStatus.NOT_FOUND, cause, messageArgs);
    }
}