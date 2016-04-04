package com.ozay.backend.model;

import org.joda.time.DateTime;

/**
 * Created by RGV Krushnan on 24-03-2016.
 */
public class CollaborateTrack {

    public Long memberId;
    public Long collaborateId;

    private boolean success;

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    private String subject;

    private Member member;
    private DateTime surveyissueDate;
    public DateTime getsurveyissueDate() {
        return surveyissueDate;
    }

    public void setSurveyissueDate(DateTime surveyissueDate) {
        this.surveyissueDate = surveyissueDate;
    }


    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCollaborateId() {
        return collaborateId;
    }

    public void setCollaborateId(Long collaborateId) {
        this.collaborateId = collaborateId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CollaborateTrack collaborateTrack = (CollaborateTrack) o;

        if (collaborateId != null ? !collaborateId.equals(collaborateTrack.collaborateId) : collaborateTrack.collaborateId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (collaborateId ^ (collaborateId >>> 32));
    }

    @Override
    public String toString() {
        return "collaborateTrack{" +
            "id=" + collaborateId +
            ", createdDate='" + surveyissueDate + "'" +
            ", member='" + member + "'" +


            '}';
    }


}
