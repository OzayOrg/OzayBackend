package com.ozay.backend.model;

import org.joda.time.DateTime;

/**
 * Created by RGV Krushnan on 21-03-2016.
 */
public class CollaborateRecord {


    public Long collaborateId;
    private boolean sent;
    private boolean surveycomplete;
    private DateTime surveycompletedate;
    private Member member;
    public Long memberId;


    public boolean SurveySent() {
        return sent;
    }

    public void setSurveySent(boolean sent) {
        this.sent = sent;
    }

    public DateTime getSurveycompletedate() {
        return surveycompletedate;
    }

    public void setSurveyCompletedDate(DateTime surveycompletedate) {
        this.surveycompletedate = surveycompletedate;
    }

    public void setCollaborateId(Long CollaborateId) {
        this.collaborateId = collaborateId;
    }

        public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
