package com.ozay.backend.model;

/**
 * Created by RGV Krushnan on 21-03-2016.
 */

import  org.joda.time.DateTime;

import java.util.List;

public class Collaborate{

    private Long id;

    private Long buildingId;

    private String subject;

    private DateTime surveyissueDate;

    private String postedby;

    private List<CollaborateRecord> collaborateRecordList;

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

    public DateTime getSurveyissueDate() {
        return surveyissueDate;
    }

    public void setSurveyissueDate(DateTime surveyissueDate) {
        this.surveyissueDate = surveyissueDate;
    }

    public String getpostedy() {
        return postedby;
    }

    public void setpostedy(String postedby) {
        this.postedby = postedby;
    }


    public List<CollaborateRecord> getCollaborateRecordList() {
        return collaborateRecordList;
    }

    public void setCollaborateRecordList(List<CollaborateRecord> collaborateRecordList) {
        this.collaborateRecordList = collaborateRecordList;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Collaborate collaborate = (Collaborate) o;

        if (id != null ? !id.equals(collaborate.id) : collaborate.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Collaborate{" +
            "id=" + id +
            ", buildingId='" + buildingId + "'" +
            ", issueDate='" + surveyissueDate + "'" +
            ", createdBy='" + postedby + "'" +
            ", subject='" + subject + "'" +
            '}';
    }
}

