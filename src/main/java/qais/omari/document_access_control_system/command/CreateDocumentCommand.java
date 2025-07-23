package qais.omari.document_access_control_system.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import qais.omari.document_access_control_system.dto.request.DocumentRequestDTO;
import qais.omari.document_access_control_system.dto.response.DocumentResponseDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDocumentCommand implements Command<DocumentResponseDTO> {
    private DocumentRequestDTO documentRequest;
    private String username;
}