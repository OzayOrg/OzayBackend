package com.ozay.backend.model;

/**
 * Created by naofumiezaki on 11/20/15.
 */
public class OrganizationUser {
    private Long id;
    private Long userId;
    private Long organizationId;
    private boolean activated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public String toString() {
        return "OrganizationUser{" +
            "id='" + id + '\'' +
            "userId='" + userId + '\'' +
            "organizationId='" + organizationId + '\'' +
            "activated='" + activated + '\'' +
            "}";
    }
}
