package qais.omari.document_access_control_system.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qais.omari.document_access_control_system.command.CreateDocumentCommand;
import qais.omari.document_access_control_system.dto.response.DocumentResponseDTO;
import qais.omari.document_access_control_system.exception.AccessDeniedException;
import qais.omari.document_access_control_system.mapper.DocumentMapper;
import qais.omari.document_access_control_system.model.Document;
import qais.omari.document_access_control_system.repository.DocumentRepository;
import qais.omari.document_access_control_system.service.DocumentPermissionService;

import java.util.ArrayList;

@Component
public class CreateDocumentCommandHandler implements CommandHandler<CreateDocumentCommand, DocumentResponseDTO> {
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private DocumentPermissionService permissionService;
    
    @Autowired
    private DocumentMapper documentMapper;
    
    @Override
    public DocumentResponseDTO handle(CreateDocumentCommand command) {
        Document document = documentMapper.toEntity(command.getDocumentRequest());
        document.setAccessibleUsers(new ArrayList<>());
        
        Document savedDocument = documentRepository.save(document);
        return documentMapper.toResponseDTO(savedDocument);
    }
}