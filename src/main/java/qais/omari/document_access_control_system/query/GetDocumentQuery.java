package qais.omari.document_access_control_system.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import qais.omari.document_access_control_system.dto.response.DocumentResponseDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDocumentQuery implements Query<DocumentResponseDTO> {
    private Long documentId;
    private String username;
}