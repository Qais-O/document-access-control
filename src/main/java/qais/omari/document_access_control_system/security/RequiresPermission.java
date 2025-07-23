package qais.omari.document_access_control_system.security;

import qais.omari.document_access_control_system.enums.Permission;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RequiresPermission {
    Permission value();

    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface Read {}

    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface Write {}

    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface Delete {}

    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface AdminOnly {}
}