package com.ozay.backend.model;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by naofumiezaki on 5/24/16.
 */
public class Collaborate extends Model{
    public static final Integer RADIO = 1;
    public static final Integer MULTIPLE_CHOICE = 2;
    public static final Integer CALENDAR = 3;

    public static final Integer STATUS_CREATED = 1;
    public static final Integer STATUS_COMPLETED = 2;
    public static final Integer STATUS_CANCELED = 3;

    private Long id;
    private Long buildingId;
    private String subject;
    private String message;
    private Integer response;
    private Long collaborateFieldId;
    private List<Member> members;
    private List<CollaborateField> collaborateFields;
    private Integer status;
    private DateTime displayUntil;
    
    
    
    public DateTime getDisplayUntil() {
        return displayUntil;
    }

    public void setDisplayUntil(DateTime displayUntil) {
        this.displayUntil = displayUntil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }

    public Long getCollaborateFieldId() {
        return collaborateFieldId;
    }

    public void setCollaborateFieldId(Long collaborateFieldId) {
        this.collaborateFieldId = collaborateFieldId;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<CollaborateField> getCollaborateFields() {
        return collaborateFields;
    }

    public void setCollaborateFields(List<CollaborateField> collaborateFields) {
        this.collaborateFields = collaborateFields;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Collaborate{" +
            "id='" + id + '\'' +
            "subject='" + subject + '\'' +
            "collaborateFieldId='" + collaborateFieldId + '\'' +
            "message='" + message + '\'' +
            "response='" + response + '\'' +
            "collaborateFields='" + collaborateFields + '\'' +
            "}";
    }
}
