package qais.omari.document_access_control_system.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ErrorCodes {
    
    // Business Error Codes
    public static final String BUSINESS_VALIDATION = "BUS-003";
    
    // Security Error Codes
    public static final String SECURITY_ACCESS_DENIED = "SEC-005";
    public static final String SECURITY_AUTHENTICATION_FAILED = "SEC-006";
    
    // Technical Error Codes
    public static final String TECHNICAL_INTERNAL_SERVER = "TEC-001";

    // Business Error Codes
    public static final class Business {
        public static final String VALIDATION = "BUS-003";
        public static final String DOCUMENT_NOT_FOUND = "BUS-001";
    }

    // Security Error Codes
    public static final class Security {
        public static final String ACCESS_DENIED = "SEC-005";
        public static final String AUTHENTICATION_FAILED = "SEC-006";
        public static final String UNAUTHORIZED = "SEC-001";
        public static final String USER_NOT_FOUND = "SEC-002";
    }

    // Technical Error Codes
    public static final class Technical {
        public static final String INTERNAL_SERVER = "TEC-001";
        public static final String VALIDATION = "TEC-003";
    }
}