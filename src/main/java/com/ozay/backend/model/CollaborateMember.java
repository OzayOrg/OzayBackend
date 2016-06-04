package com.ozay.backend.model;

import org.joda.time.DateTime;

/**
 * Created by naofumiezaki on 6/7/16.
 */
public class CollaborateMember {
    private Long collaborateDateId;
    private Boolean selected; // For RSVP
    private Member member;
    private DateTime modifiedDate;


    public DateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
