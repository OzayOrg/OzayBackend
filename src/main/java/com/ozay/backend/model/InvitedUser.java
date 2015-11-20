package com.ozay.backend.model;

import java.util.Set;

/**
 * Created by naofumiezaki on 11/19/15.
 */
public class InvitedUser {
    private Long id;
    private Long organizationId;
    private String firstName;
    private String lastName;
    private String email;
    private boolean activated;
    private Set<OrganizationPermission> organizationPermissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Set<OrganizationPermission> getOrganizationPermissions() {
        return organizationPermissions;
    }

    public void setOrganizationPermissions(Set<OrganizationPermission> organizationPermissions) {
        this.organizationPermissions = organizationPermissions;
    }
}
