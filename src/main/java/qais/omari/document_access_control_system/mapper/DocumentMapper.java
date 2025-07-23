package qais.omari.document_access_control_system.mapper;

import org.springframework.stereotype.Component;
import qais.omari.document_access_control_system.dto.request.DocumentRequestDTO;
import qais.omari.document_access_control_system.dto.response.DocumentResponseDTO;
import qais.omari.document_access_control_system.dto.common.DocumentAccessDTO;
import qais.omari.document_access_control_system.model.Document;
import qais.omari.document_access_control_system.model.DocumentUserAccess;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DocumentMapper {
    
    public Document toEntity(DocumentRequestDTO dto) {
        Document document = new Document();
        document.setName(dto.getName());
        document.setContent(dto.getContent());
        document.setFileType(dto.getFileType());
        return document;
    }
    
    public DocumentRequestDTO toRequestDTO(Document document) {
        DocumentRequestDTO dto = new DocumentRequestDTO();
        dto.setName(document.getName());
        dto.setContent(document.getContent());
        dto.setFileType(document.getFileType());
        return dto;
    }
    
    public DocumentResponseDTO toResponseDTO(Document document) {
        DocumentResponseDTO dto = new DocumentResponseDTO();
        dto.setId(document.getId());
        dto.setName(document.getName());
        dto.setContent(document.getContent());
        dto.setFileType(document.getFileType());
        // Removed owner field
        
        // Map accessible users without circular reference
        List<DocumentAccessDTO> accessList = document.getAccessibleUsers() != null ? 
            document.getAccessibleUsers().stream()
                .map(this::toAccessDTO)
                .collect(Collectors.toList()) : 
        Collections.emptyList();
        
        dto.setAccessibleUsers(accessList);
        return dto;
    }
    
    public List<DocumentResponseDTO> toResponseDTOList(List<Document> documents) {
        return documents.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    private DocumentAccessDTO toAccessDTO(DocumentUserAccess access) {
        return new DocumentAccessDTO(access.getUsername(), access.getPermission());
    }
}