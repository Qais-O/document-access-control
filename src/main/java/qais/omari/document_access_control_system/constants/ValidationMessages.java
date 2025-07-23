package qais.omari.document_access_control_system.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ValidationMessages {
    
    public static final String USERNAME_REQUIRED = "{" + MessageKeys.VALIDATION_USERNAME_REQUIRED + "}";
    public static final String PERMISSION_REQUIRED = "{" + MessageKeys.VALIDATION_PERMISSION_REQUIRED + "}";
    public static final String DOCUMENT_NAME_REQUIRED = "{" + MessageKeys.VALIDATION_DOCUMENT_NAME_REQUIRED + "}";
    public static final String DOCUMENT_NAME_SIZE = "{" + MessageKeys.VALIDATION_DOCUMENT_NAME_SIZE + "}";
    public static final String CONTENT_NULL = "{" + MessageKeys.VALIDATION_CONTENT_NULL + "}";
    public static final String CONTENT_SIZE = "{" + MessageKeys.VALIDATION_CONTENT_SIZE + "}";
    public static final String FILE_TYPE_REQUIRED = "{" + MessageKeys.VALIDATION_FILE_TYPE_REQUIRED + "}";
    public static final String FILE_TYPE_SIZE = "{" + MessageKeys.VALIDATION_FILE_TYPE_SIZE + "}";
    public static final String DOCUMENT_IDS_EMPTY = "{" + MessageKeys.VALIDATION_DOCUMENT_IDS_EMPTY + "}";
}