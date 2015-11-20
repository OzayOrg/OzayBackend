package com.ozay.backend.web.rest.dto.pages;

import com.ozay.backend.model.Permission;
import com.ozay.backend.model.Role;

import java.util.List;


/**
 * Created by naofumiezaki on 11/17/15.
 */
public class PageOrganizationUserDTO {

    private List<Permission> permissions;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

}
