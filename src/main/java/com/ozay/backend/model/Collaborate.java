package com.ozay.backend.model;

import java.util.List;

/**
 * Created by naofumiezaki on 5/24/16.
 */
public class Collaborate extends Model{
    public static Integer RSVP = 1;
    public static Integer CALENDER = 2;

    public static Integer STATUS_CREATED = 1;
    public static Integer STATUS_COMPLETED = 2;
    public static Integer STATUS_CANCELED = 3;

    private Long id;
    private Long buildingId;
    private String subject;
    private String message;
    private Integer response;
    private Long collaborateDateId;
    private List<Member> members;
    private List<CollaborateDate> collaborateDates;
    private Integer status;



    public static Integer getStatusCreated() {
        return STATUS_CREATED;
    }

    public static void setStatusCreated(Integer statusCreated) {
        STATUS_CREATED = statusCreated;
    }

    public static Integer getStatusCompleted() {
        return STATUS_COMPLETED;
    }

    public static void setStatusCompleted(Integer statusCompleted) {
        STATUS_COMPLETED = statusCompleted;
    }

    public static Integer getStatusCanceled() {
        return STATUS_CANCELED;
    }

    public static void setStatusCanceled(Integer statusCanceled) {
        STATUS_CANCELED = statusCanceled;
    }

    public Long getCollaborateDateId() {
        return collaborateDateId;
    }

    public void setCollaborateDateId(Long collaborateDateId) {
        this.collaborateDateId = collaborateDateId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static Integer getRSVP() {
        return RSVP;
    }

    public static void setRSVP(Integer RSVP) {
        Collaborate.RSVP = RSVP;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public static Integer getCALENDER() {
        return CALENDER;
    }

    public static void setCALENDER(Integer CALENDER) {
        Collaborate.CALENDER = CALENDER;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }


    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<CollaborateDate> getCollaborateDates() {
        return collaborateDates;
    }

    public void setCollaborateDates(List<CollaborateDate> collaborateDates) {
        this.collaborateDates = collaborateDates;
    }

    @Override
    public String toString() {
        return "Collaborate{" +
            "id='" + id + '\'' +
            "subject='" + subject + '\'' +
            "collaborateDateId='" + collaborateDateId + '\'' +
            "message='" + message + '\'' +
            "response='" + response + '\'' +
            "collaborateDates='" + collaborateDates + '\'' +
            "}";
    }
}
