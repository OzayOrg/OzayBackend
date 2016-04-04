package com.ozay.backend.web.rest.dto.pages;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.Member;
import com.ozay.backend.model.Role;

import java.util.*;
/**
 * Created by RGV Krushnan on 25-03-2016.
 */
public class PageCollaborateDTO {

    private List<Collaborate> collaborate;
    private List<Role> roles;
    private List<Member> members;

    public List<Collaborate> getCollaborate() {
        return collaborate;
    }

    public void setNotifications(List<Collaborate> collaborate) {
        this.collaborate = collaborate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

}
