package qais.omari.document_access_control_system.dto.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import qais.omari.document_access_control_system.enums.Permission;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentAccessDTO {
    private String username;
    private Permission permission;
}