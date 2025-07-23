package qais.omari.document_access_control_system.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qais.omari.document_access_control_system.command.GrantAccessCommand;
import qais.omari.document_access_control_system.dto.response.DocumentResponseDTO;
import qais.omari.document_access_control_system.exception.DocumentNotFoundException;
import qais.omari.document_access_control_system.exception.UnauthorizedException;
import qais.omari.document_access_control_system.exception.UserNotFoundException;
import qais.omari.document_access_control_system.mapper.DocumentMapper;
import qais.omari.document_access_control_system.model.Document;
import qais.omari.document_access_control_system.model.DocumentUserAccess;
import qais.omari.document_access_control_system.model.User;
import qais.omari.document_access_control_system.repository.DocumentRepository;
import qais.omari.document_access_control_system.repository.UserRepository;
import qais.omari.document_access_control_system.service.DocumentPermissionService;

import java.util.ArrayList;

@Component
public class GrantAccessCommandHandler implements CommandHandler<GrantAccessCommand, DocumentResponseDTO> {
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DocumentPermissionService permissionService;
    
    @Autowired
    private DocumentMapper documentMapper;
    
    @Override
    public DocumentResponseDTO handle(GrantAccessCommand command) {
        if (!permissionService.canGrantAccess(command.getCurrentUsername(), command.getDocumentId())) {
            throw new UnauthorizedException();
        }
        
        Document document = documentRepository.findById(command.getDocumentId())
                .orElseThrow(() -> new DocumentNotFoundException(command.getDocumentId()));
        
        String targetUsername = command.getGrantRequest().getUsername();
        User targetUser = userRepository.findById(targetUsername).orElse(null);
        if (targetUser == null) {
            throw new UserNotFoundException(targetUsername);
        }
        
        boolean userAlreadyHasThisPermission = document.getAccessibleUsers().stream()
                .anyMatch(access -> access.getUsername().equals(targetUsername) 
                             && access.getPermission().equals(command.getGrantRequest().getPermission()));
        
        if (userAlreadyHasThisPermission) {
            return documentMapper.toResponseDTO(document);
        }
        
        DocumentUserAccess newAccess = new DocumentUserAccess();
        newAccess.setUsername(targetUsername);
        newAccess.setPermission(command.getGrantRequest().getPermission());
        newAccess.setDocument(document);
        
        if (document.getAccessibleUsers() == null) {
            document.setAccessibleUsers(new ArrayList<>());
        }
        document.getAccessibleUsers().add(newAccess);
        
        Document savedDocument = documentRepository.save(document);
        return documentMapper.toResponseDTO(savedDocument);
    }
}