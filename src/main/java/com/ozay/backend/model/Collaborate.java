package com.ozay.backend.model;

import java.util.List;

/**
 * Created by naofumiezaki on 5/24/16.
 */
public class Collaborate {
    public static Integer RSVP = 1;
    public static Integer CALENDER = 2;

    private Long id;
    private Long buildingId;
    private String subject;
    private Integer response;
    private Long createdBy;
    private Long modifiedBy;
    private boolean tracking;
    private List<CollaborateDate> collaborateIssueDateList;

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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

    public boolean isTracking() {
        return tracking;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }

    public List<CollaborateDate> getCollaborateIssueDateList() {
        return collaborateIssueDateList;
    }

    public void setCollaborateIssueDateList(List<CollaborateDate> collaborateIssueDateList) {
        this.collaborateIssueDateList = collaborateIssueDateList;
    }
}
