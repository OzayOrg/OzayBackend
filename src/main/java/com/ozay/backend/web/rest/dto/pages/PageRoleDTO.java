package com.ozay.backend.web.rest.dto.pages;

import com.ozay.backend.model.Permission;
import com.ozay.backend.model.Role;

import java.util.List;


/**
 * Created by naofumiezaki on 11/17/15.
 */
public class PageRoleDTO {
    private List<Permission> permissions;
    private List<Role> roles;
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
