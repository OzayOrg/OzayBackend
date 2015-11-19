package com.ozay.backend.web.rest.dto.pages;

import com.ozay.backend.model.Permission;
import com.ozay.backend.model.Role;

import java.util.List;


/**
 * Created by naofumiezaki on 11/17/15.
 */
public class PageRoleEditDTO {
    private Role role;
    private List<Permission> permissions;
    private List<Role> roles;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
