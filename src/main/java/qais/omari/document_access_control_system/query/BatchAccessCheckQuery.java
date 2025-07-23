package qais.omari.document_access_control_system.query;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import qais.omari.document_access_control_system.dto.request.BatchAccessCheckRequestDTO;
import qais.omari.document_access_control_system.dto.response.BatchAccessCheckResponseDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchAccessCheckQuery implements Query<BatchAccessCheckResponseDTO> {
    private String username;
    private BatchAccessCheckRequestDTO request;
}