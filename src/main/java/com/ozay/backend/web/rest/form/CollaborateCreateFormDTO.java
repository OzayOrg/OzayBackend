package com.ozay.backend.web.rest.form;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.Role;

import java.util.List;

/**
 * Created by naofumiezaki on 5/27/16.
 */
public class CollaborateCreateFormDTO {
    private Collaborate collaborate;
    private List<Role> roles;

    public Collaborate getCollaborate() {
        return collaborate;
    }

    public void setCollaborate(Collaborate collaborate) {
        this.collaborate = collaborate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
