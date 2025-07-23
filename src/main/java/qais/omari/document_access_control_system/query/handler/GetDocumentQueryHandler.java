package qais.omari.document_access_control_system.query.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qais.omari.document_access_control_system.dto.response.DocumentResponseDTO;
import qais.omari.document_access_control_system.mapper.DocumentMapper;
import qais.omari.document_access_control_system.model.Document;
import qais.omari.document_access_control_system.query.GetDocumentQuery;
import qais.omari.document_access_control_system.repository.DocumentRepository;
import qais.omari.document_access_control_system.service.DocumentPermissionService;

@Component
public class GetDocumentQueryHandler implements QueryHandler<GetDocumentQuery, DocumentResponseDTO> {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentPermissionService permissionService;

    @Autowired
    private DocumentMapper documentMapper;
    
    @Override
    public DocumentResponseDTO handle(GetDocumentQuery query) {
        if (!permissionService.canRead(query.getUsername(), query.getDocumentId())) {
            throw new SecurityException("Access denied");
        }
        
        Document document = documentRepository.findById(query.getDocumentId())
            .orElseThrow(() -> new RuntimeException("Document not found"));
        
        return documentMapper.toResponseDTO(document);
    }
}