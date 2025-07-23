package qais.omari.document_access_control_system.query.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qais.omari.document_access_control_system.dto.response.BatchAccessCheckResponseDTO;
import qais.omari.document_access_control_system.query.BatchAccessCheckQuery;
import qais.omari.document_access_control_system.service.DocumentPermissionService;

import java.util.List;

@Component
public class BatchAccessCheckQueryHandler implements QueryHandler<BatchAccessCheckQuery, BatchAccessCheckResponseDTO> {
    
    @Autowired
    private DocumentPermissionService permissionService;
    
    @Override
    public BatchAccessCheckResponseDTO handle(BatchAccessCheckQuery query) {
        if (query.getRequest().getDocumentIds() == null || query.getRequest().getDocumentIds().isEmpty()) {
            throw new IllegalArgumentException("Document IDs list cannot be empty");
        }
        
        if (query.getRequest().getPermission() == null) {
            throw new IllegalArgumentException("Permission type is required");
        }
        
        // Get accessible document IDs using service
        List<Long> accessibleIds = permissionService.getAccessibleDocumentIds(
                query.getUsername(), 
                query.getRequest().getDocumentIds(), 
                query.getRequest().getPermission()
        );
        
        return new BatchAccessCheckResponseDTO(accessibleIds);
    }
}