package qais.omari.document_access_control_system.controller.api.v1;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import qais.omari.document_access_control_system.command.*;
import qais.omari.document_access_control_system.dto.request.BatchAccessCheckRequestDTO;
import qais.omari.document_access_control_system.dto.request.DocumentRequestDTO;
import qais.omari.document_access_control_system.dto.request.GrantAccessRequestDTO;
import qais.omari.document_access_control_system.dto.response.BatchAccessCheckResponseDTO;
import qais.omari.document_access_control_system.dto.response.DocumentResponseDTO;
import qais.omari.document_access_control_system.middleware.CommandBus;
import qais.omari.document_access_control_system.middleware.QueryBus;
import qais.omari.document_access_control_system.query.*;
import qais.omari.document_access_control_system.security.RequiresPermission;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    @Autowired
    private QueryBus queryBus;

    @Autowired
    private CommandBus commandBus;


    @RequiresPermission.Read
    @GetMapping("/{id}")
    public DocumentResponseDTO getDocument(@PathVariable Long id, Authentication authentication) {
        GetDocumentQuery query = new GetDocumentQuery(id, authentication.getName());
        return queryBus.execute(query);
    }

    @GetMapping
    public List<DocumentResponseDTO> getAccessibleDocuments(Authentication authentication) {
        GetAccessibleDocumentsQuery query = new GetAccessibleDocumentsQuery(authentication.getName());
        return queryBus.execute(query);
    }

    @PostMapping("/access-check")
    public BatchAccessCheckResponseDTO checkBatchAccess(@Valid @RequestBody BatchAccessCheckRequestDTO request,
                                                       Authentication authentication) {
        BatchAccessCheckQuery query = new BatchAccessCheckQuery(authentication.getName(), request);
        return queryBus.execute(query);
    }

    @RequiresPermission.AdminOnly
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public DocumentResponseDTO createDocument(@Valid @RequestBody DocumentRequestDTO documentRequest,
                                            Authentication authentication) {
        CreateDocumentCommand command = new CreateDocumentCommand(documentRequest, authentication.getName());
        return commandBus.execute(command);
    }

    @RequiresPermission.Delete
    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable Long id, Authentication authentication) {
        DeleteDocumentCommand command = new DeleteDocumentCommand(id, authentication.getName());
        commandBus.execute(command);
    }

    @PostMapping("/{id}/grant")
    public DocumentResponseDTO grantAccess(@PathVariable Long id,
                                         @Valid @RequestBody GrantAccessRequestDTO grantRequest,
                                         Authentication authentication) {
        GrantAccessCommand command = new GrantAccessCommand(id, grantRequest, authentication.getName());
        return commandBus.execute(command);
    }
}