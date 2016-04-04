package com.ozay.backend.web.rest.form;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.Member;

import java.util.List;

/**
 * Created by RGV Krushnan on 25-03-2016.
 */
public class CollaborateFormDTO {
    private Collaborate collaborate;
    private List<Member> members;
    //private List<Building> buildings;
    private List<CollaborateRoleFormDTO> roles;
    private boolean result;

    public Collaborate getCollaborate() {
        return collaborate;
    }

    public void setCollaborate(Collaborate collaborate) {
        this.collaborate = collaborate;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<CollaborateRoleFormDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<CollaborateRoleFormDTO> roles) {
        this.roles = roles;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CollaborateFormDTO{" +
            "collaborate='" + collaborate + '\'' +
            "List<Member>='" + members + '\'' +
            "roles='" + roles + '\'' +
            "}";
    }
}
