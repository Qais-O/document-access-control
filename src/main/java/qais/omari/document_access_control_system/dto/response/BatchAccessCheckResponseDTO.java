package qais.omari.document_access_control_system.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchAccessCheckResponseDTO {
    
    private List<Long> accessibleIds;
}