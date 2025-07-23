package qais.omari.document_access_control_system.query.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qais.omari.document_access_control_system.dto.response.DocumentResponseDTO;
import qais.omari.document_access_control_system.mapper.DocumentMapper;
import qais.omari.document_access_control_system.model.Document;
import qais.omari.document_access_control_system.query.GetAccessibleDocumentsQuery;
import qais.omari.document_access_control_system.service.DocumentPermissionService;

import java.util.List;

@Component
public class GetAccessibleDocumentsQueryHandler implements QueryHandler<GetAccessibleDocumentsQuery, List<DocumentResponseDTO>> {
    
    @Autowired
    private DocumentPermissionService permissionService;
    
    @Autowired
    private DocumentMapper documentMapper;
    
    @Override
    public List<DocumentResponseDTO> handle(GetAccessibleDocumentsQuery query) {
        List<Document> documents = permissionService.getReadableDocuments(query.getUsername());
        return documentMapper.toResponseDTOList(documents);
    }
}