package qais.omari.document_access_control_system.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteDocumentCommand implements Command<Void> {
    private Long documentId;
    private String username;
}