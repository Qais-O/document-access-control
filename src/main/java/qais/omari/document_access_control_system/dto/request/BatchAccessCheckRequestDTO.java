package qais.omari.document_access_control_system.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import qais.omari.document_access_control_system.constants.ValidationMessages;
import qais.omari.document_access_control_system.enums.Permission;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchAccessCheckRequestDTO {
    
    @NotNull(message = ValidationMessages.PERMISSION_REQUIRED)
    private Permission permission;
    
    @NotEmpty(message = ValidationMessages.DOCUMENT_IDS_EMPTY)
    private List<Long> documentIds;
}