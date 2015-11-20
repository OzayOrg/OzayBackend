package com.ozay.backend.web.rest.dto;

import com.ozay.backend.model.OrganizationPermission;

import java.util.Set;

/**
 * Created by naofumiezaki on 11/19/15.
 */
public class OrganizationUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long organizationId;
    private Set<OrganizationPermission> organizationPermissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Set<OrganizationPermission> getOrganizationPermissions() {
        return organizationPermissions;
    }

    public void setOrganizationPermissions(Set<OrganizationPermission> organizationPermissions) {
        this.organizationPermissions = organizationPermissions;
    }
}
