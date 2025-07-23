package qais.omari.document_access_control_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import qais.omari.document_access_control_system.enums.Permission;

@Entity
@Table(name = "document_user_access",
       uniqueConstraints = @UniqueConstraint(columnNames = {"username", "document_id", "permission"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "document")
public class DocumentUserAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String username;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Permission permission;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    @JsonBackReference
    private Document document;
}