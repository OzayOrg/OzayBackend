package com.ozay.backend.model;

/**
 * Created by naofumiezaki on 10/31/15.
 */
public class NotificationRecord {
    public Long memberId;
    public Long notificationId;

    private boolean success;
    private boolean track;
    private boolean complete;
    private String email;
    private String note;

    private Member member;


    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isTrack() {
        return track;
    }

    public boolean isComplete() {
        return complete;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTrack(boolean track) {
        this.track = track;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
