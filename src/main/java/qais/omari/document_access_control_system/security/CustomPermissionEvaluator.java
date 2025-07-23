package qais.omari.document_access_control_system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import qais.omari.document_access_control_system.enums.Permission;
import qais.omari.document_access_control_system.service.DocumentPermissionService;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private DocumentPermissionService documentPermissionService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || permission == null) {
            return false;
        }

        String username = authentication.getName();
        
        if (targetDomainObject instanceof Long) {
            Long documentId = (Long) targetDomainObject;
            Permission perm = Permission.valueOf(permission.toString());
            return documentPermissionService.hasPermission(username, documentId, perm);
        }
        
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (authentication == null || targetId == null || permission == null) {
            return false;
        }

        String username = authentication.getName();
        
        if ("DOCUMENT".equals(targetType) && targetId instanceof Long) {
            Long documentId = (Long) targetId;
            Permission perm = Permission.valueOf(permission.toString());
            return documentPermissionService.hasPermission(username, documentId, perm);
        }
        
        return false;
    }
}