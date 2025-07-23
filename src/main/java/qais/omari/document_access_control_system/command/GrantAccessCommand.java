package qais.omari.document_access_control_system.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import qais.omari.document_access_control_system.dto.request.GrantAccessRequestDTO;
import qais.omari.document_access_control_system.dto.response.DocumentResponseDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrantAccessCommand implements Command<DocumentResponseDTO> {
    private Long documentId;
    private GrantAccessRequestDTO grantRequest;
    private String currentUsername;
}