package qais.omari.document_access_control_system.exception;

import org.springframework.http.HttpStatus;
import qais.omari.document_access_control_system.constants.MessageKeys;

public class DocumentNotFoundException extends BusinessException {

    public DocumentNotFoundException(Object... messageArgs) {
        super(MessageKeys.Subcode.DOCUMENT_NOT_FOUND, MessageKeys.Error.DOCUMENT_NOT_FOUND, HttpStatus.NOT_FOUND, messageArgs);
    }

    public DocumentNotFoundException(Throwable cause, Object... messageArgs) {
        super(MessageKeys.Subcode.DOCUMENT_NOT_FOUND, MessageKeys.Error.DOCUMENT_NOT_FOUND, HttpStatus.NOT_FOUND, cause, messageArgs);
    }
}