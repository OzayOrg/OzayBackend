package com.ozay.backend.web.rest.dto.pages.partials;

import org.joda.time.DateTime;

/**
 * Created by naofumiezaki on 6/26/16.
 */
public class CollaborateRecordDTO {
    private Long id;
    private Long collaborateFieldId;
    private DateTime createdDate;
    private String message;
    private Integer response;
    private Integer status;
    private String subject;
    private Integer total;
    private Integer numOfReplies;

    public CollaborateRecordDTO() {

    }


    public CollaborateRecordDTO(Long id, Long collaborateFieldId, DateTime createdDate, String message, Integer response, Integer status, String subject, Integer total, Integer numOfReplies) {
        this.id = id;
        this.collaborateFieldId = collaborateFieldId;
        this.createdDate = createdDate;
        this.message = message;
        this.response = response;
        this.status = status;
        this.subject = subject;
        this.total = total;
        this.numOfReplies = numOfReplies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollaborateFieldId() {
        return collaborateFieldId;
    }

    public void setCollaborateFieldId(Long collaborateFieldId) {
        this.collaborateFieldId = collaborateFieldId;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getNumOfReplies() {
        return numOfReplies;
    }

    public void setNumOfReplies(Integer numOfReplies) {
        this.numOfReplies = numOfReplies;
    }
}
