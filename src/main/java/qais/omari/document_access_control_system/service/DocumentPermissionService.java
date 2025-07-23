package qais.omari.document_access_control_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qais.omari.document_access_control_system.enums.Permission;
import qais.omari.document_access_control_system.enums.Role;
import qais.omari.document_access_control_system.model.Document;
import qais.omari.document_access_control_system.model.User;
import qais.omari.document_access_control_system.repository.DocumentRepository;
import qais.omari.document_access_control_system.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentPermissionService {
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public boolean hasPermission(String username, Long documentId, Permission permission) {
        Document document = documentRepository.findById(documentId).orElse(null);
        
        if (document == null) {
            return false;
        }
        
        // Everyone needs explicit permissions - no owner privileges
        return document.getAccessibleUsers().stream()
            .anyMatch(access -> access.getUsername().equals(username) && 
                      access.getPermission().equals(permission));
    }
    
    public boolean canRead(String username, Long documentId) {
        return hasPermission(username, documentId, Permission.READ);
    }
    
    public boolean canWrite(String username, Long documentId) {
        return hasPermission(username, documentId, Permission.WRITE);
    }
    
    public boolean canDelete(String username, Long documentId) {
        return hasPermission(username, documentId, Permission.DELETE);
    }
    
    public boolean canCreate(String username) {
        // Only admins can create documents
        return isAdmin(username);
    }
    
    public boolean canGrantAccess(String username, Long documentId) {
        // Admin users can always grant access to any document
        if (isAdmin(username)) {
            return true;
        }
        
        // Users with WRITE permission can grant access
        return hasPermission(username, documentId, Permission.WRITE);
    }
    
    public boolean isAdmin(String username) {
        User user = userRepository.findById(username).orElse(null);
        return user != null && user.getRole() == Role.ADMIN;
    }
    
    // Get documents user can read
    public List<Document> getReadableDocuments(String username) {
        // Use repository method to get documents user has any access to
        List<Document> allAccessibleDocs = documentRepository.findAccessibleDocuments(username);
        
        // Filter to only documents with explicit READ permission
        return allAccessibleDocs.stream()
                .filter(doc -> doc.getAccessibleUsers().stream()
                                  .anyMatch(access -> access.getUsername().equals(username) && 
                                                    access.getPermission().equals(Permission.READ)))
                .collect(Collectors.toList());
    }

    /**
     * Check permissions for multiple documents in batch
     * Special case: Admin users have access to all permissions for any existing document
     * since they can grant themselves access if needed
     * 
     * @param username the username to check permissions for
     * @param documentIds list of document IDs to check
     * @param permission the permission type to check
     * @return list of document IDs where user has the specified permission or is admin and document exists
     */
    public List<Long> getAccessibleDocumentIds(String username, List<Long> documentIds, Permission permission) {
        // Special case: If user is admin, return all existing document IDs
        if (isAdmin(username)) {
            return documentIds.stream()
                    .filter(documentId -> documentRepository.existsById(documentId))
                    .collect(Collectors.toList());
        }
        
        // Regular permission check for non-admin users
        return documentIds.stream()
                .filter(documentId -> hasPermission(username, documentId, permission))
                .collect(Collectors.toList());
    }
}