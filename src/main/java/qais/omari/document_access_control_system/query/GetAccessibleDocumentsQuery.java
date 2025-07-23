package qais.omari.document_access_control_system.query;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import qais.omari.document_access_control_system.dto.response.DocumentResponseDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccessibleDocumentsQuery implements Query<List<DocumentResponseDTO>> {
    private String username;
}