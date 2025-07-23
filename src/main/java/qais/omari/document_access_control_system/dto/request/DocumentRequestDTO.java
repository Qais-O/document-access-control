package qais.omari.document_access_control_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import qais.omari.document_access_control_system.constants.ValidationMessages;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequestDTO {

    @NotBlank(message = ValidationMessages.DOCUMENT_NAME_REQUIRED)
    @Size(min = 1, max = 255, message = ValidationMessages.DOCUMENT_NAME_SIZE)
    private String name;

    @NotNull(message = ValidationMessages.CONTENT_NULL)
    @Size(max = 10000, message = ValidationMessages.CONTENT_SIZE)
    private String content;

    @NotBlank(message = ValidationMessages.FILE_TYPE_REQUIRED)
    @Size(min = 1, max = 50, message = ValidationMessages.FILE_TYPE_SIZE)
    private String fileType;
}