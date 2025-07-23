package qais.omari.document_access_control_system.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class MessageKeys {

    // Validation Messages
    public static final String VALIDATION_PERMISSION_REQUIRED = "validation.permission.required";
    public static final String VALIDATION_USERNAME_REQUIRED = "validation.username.required";
    public static final String VALIDATION_DOCUMENT_NAME_REQUIRED = "validation.document.name.required";
    public static final String VALIDATION_DOCUMENT_NAME_SIZE = "validation.document.name.size";
    public static final String VALIDATION_CONTENT_NULL = "validation.content.null";
    public static final String VALIDATION_CONTENT_SIZE = "validation.content.size";
    public static final String VALIDATION_FILE_TYPE_REQUIRED = "validation.file.type.required";
    public static final String VALIDATION_FILE_TYPE_SIZE = "validation.file.type.size";
    public static final String VALIDATION_DOCUMENT_IDS_EMPTY = "validation.document.ids.empty";

    public static final class Error {
        // Business Errors
        public static final String DOCUMENT_NOT_FOUND = "error.document.not.found";

        // Security Errors
        public static final String ACCESS_DENIED = "error.access.denied";
        public static final String UNAUTHORIZED = "error.unauthorized";
        public static final String USER_NOT_FOUND = "error.user.not.found";

        // Technical Errors
        public static final String INTERNAL_SERVER = "error.internal.server";
        public static final String AUTHENTICATION_FAILED = "error.authentication.failed";
        public static final String VALIDATION_FAILED = "error.validation.failed";
    }

    // Subcode Message Keys
    public static final class Subcode {
        public static final String DOCUMENT_NOT_FOUND = "subcode.document.not.found";
        public static final String ACCESS_DENIED = "subcode.access.denied";
        public static final String UNAUTHORIZED = "subcode.unauthorized";
        public static final String USER_NOT_FOUND = "subcode.user.not.found";
    }
}