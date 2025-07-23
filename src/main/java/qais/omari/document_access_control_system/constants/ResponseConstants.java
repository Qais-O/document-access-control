package qais.omari.document_access_control_system.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ResponseConstants {
    
    // HTTP Response Fields
    public static final class Fields {
        public static final String TIMESTAMP = "timestamp";
        public static final String STATUS = "status";
        public static final String ERROR = "error";
        public static final String ERROR_CODE = "errorCode";
        public static final String MESSAGE = "message";
        public static final String PATH = "path";
        public static final String ERRORS = "errors";
    }
    
    // HTTP Headers
    public static final class Headers {
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        public static final String URI_PREFIX = "uri=";
    }
    
    // Error Types
    public static final class ErrorTypes {
        public static final String ACCESS_DENIED = "Access Denied";
        public static final String AUTHENTICATION_FAILED = "Authentication Failed";
        public static final String VALIDATION_FAILED = "Validation Failed";
        public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    }
}