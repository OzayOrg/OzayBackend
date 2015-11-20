package com.ozay.backend.model;

/**
 * Created by naofumiezaki on 10/31/15.
 */
public class OrganizationPermission {
    private Long userId;
    private Long organizationId;
    private Long permissionId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
