package com.ozay.backend.web.rest.form;

import com.ozay.backend.model.Member;
import com.ozay.backend.model.Notification;
import com.ozay.backend.model.Role;

import java.util.List;

/**
 * Created by naofumiezaki on 11/23/15.
 */
public class NotificationFormDTO {
    private Notification notification;
    private List<Member> members;
    private List<NotificationRole> roles;

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<NotificationRole> getRoles() {
        return roles;
    }

    public void setRoles(List<NotificationRole> roles) {
        this.roles = roles;
    }

    public class NotificationRole{
        private Long roleId;
        private boolean checked;

        public Long getRoleId() {
            return roleId;
        }

        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }
 }
