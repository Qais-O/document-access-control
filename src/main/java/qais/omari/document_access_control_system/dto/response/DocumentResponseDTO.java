package qais.omari.document_access_control_system.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import qais.omari.document_access_control_system.dto.common.DocumentAccessDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponseDTO {
    private Long id;
    private String name;
    private String content;
    private String fileType;
    private String owner;
    private List<DocumentAccessDTO> accessibleUsers;
}