package qais.omari.document_access_control_system.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qais.omari.document_access_control_system.command.DeleteDocumentCommand;
import qais.omari.document_access_control_system.exception.AccessDeniedException;
import qais.omari.document_access_control_system.exception.DocumentNotFoundException;
import qais.omari.document_access_control_system.repository.DocumentRepository;
import qais.omari.document_access_control_system.service.DocumentPermissionService;

@Component
public class DeleteDocumentCommandHandler implements CommandHandler<DeleteDocumentCommand, Void> {
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private DocumentPermissionService permissionService;
    
    @Override
    public Void handle(DeleteDocumentCommand command) {
        if (!documentRepository.existsById(command.getDocumentId())) {
            throw new DocumentNotFoundException(command.getDocumentId());
        }
        
        if (!permissionService.canDelete(command.getUsername(), command.getDocumentId())) {
            throw new AccessDeniedException();
        }
        
        documentRepository.deleteById(command.getDocumentId());
        return null;
    }
}