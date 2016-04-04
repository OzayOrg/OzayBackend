package com.ozay.backend.web.rest.form;

/**
 * Created by RGV Krushnan on 25-03-2016.
 */
public class CollaborateRoleFormDTO {
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

    @Override
    public String toString() {
        return "CollaborateRoleFormDTO{" +
            "roleId='" + roleId + '\'' +
            "checked'" + checked + '\'' +
            "}";
    }

}
