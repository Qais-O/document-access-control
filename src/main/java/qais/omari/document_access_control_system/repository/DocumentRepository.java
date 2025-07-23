package qais.omari.document_access_control_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import qais.omari.document_access_control_system.enums.Permission;
import qais.omari.document_access_control_system.model.Document;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    // Removed findByOwner method - no longer needed

    @Query("SELECT d FROM Document d JOIN d.accessibleUsers du WHERE du.username = :username AND du.permission = :permission")
    List<Document> findByUsernameAndPermission(@Param("username") String username, @Param("permission") Permission permission);

    @Query("SELECT DISTINCT d FROM Document d WHERE EXISTS (SELECT du FROM DocumentUserAccess du WHERE du.document = d AND du.username = :username)")
    List<Document> findAccessibleDocuments(@Param("username") String username);
}