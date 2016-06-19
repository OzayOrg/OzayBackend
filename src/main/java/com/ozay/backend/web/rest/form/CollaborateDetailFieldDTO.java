package com.ozay.backend.web.rest.form;

import com.ozay.backend.model.Member;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by naofumiezaki on 6/10/16.
 */
public class CollaborateDetailFieldDTO {
    private Long collaborateDateId; // for Collaborate Dates, response = 2
    private Boolean selected;

    public Long getCollaborateDateId() {
        return collaborateDateId;
    }

    public void setCollaborateDateId(Long collaborateDateId) {
        this.collaborateDateId = collaborateDateId;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

}