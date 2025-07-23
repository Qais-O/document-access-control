package qais.omari.document_access_control_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import qais.omari.document_access_control_system.constants.ValidationMessages;
import qais.omari.document_access_control_system.enums.Permission;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrantAccessRequestDTO {
    
    @NotBlank(message = ValidationMessages.USERNAME_REQUIRED)
    private String username;
    
    @NotNull(message = ValidationMessages.PERMISSION_REQUIRED)
    private Permission permission;
}