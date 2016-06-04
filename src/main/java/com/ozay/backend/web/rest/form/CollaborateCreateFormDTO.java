package com.ozay.backend.web.rest.form;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.CollaborateDate;
import com.ozay.backend.model.Member;
import com.ozay.backend.model.Role;

import java.util.List;

/**
 * Created by naofumiezaki on 5/27/16.
 */
public class CollaborateCreateFormDTO {
    private Collaborate collaborate;
    private List<CollaborateDate> collaborateDates;
    private List<Role> roles;
    private List<Member> members;

    public List<CollaborateDate> getCollaborateDates() {
        return collaborateDates;
    }

    public void setCollaborateDates(List<CollaborateDate> collaborateDates) {
        this.collaborateDates = collaborateDates;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
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
