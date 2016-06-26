package com.ozay.backend.web.rest.dto.pages.partials;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by naofumiezaki on 6/24/16.
 */
public class CollaborateDTO {
    private Long id;
    private Long collaborateFieldId;
    private DateTime createdDate;
    private String message;
    private Integer response;
    private Integer status;
    private String subject;

    private List<CollaborateFieldDTO> collaborateFieldDTOs;

    public CollaborateDTO(){};

    public CollaborateDTO(Long id, Long collaborateFieldId, DateTime createdDate, String message, Integer response, Integer status, String subject) {
        this.id = id;
        this.collaborateFieldId = collaborateFieldId;
        this.createdDate = createdDate;
        this.message = message;
        this.response = response;
        this.status = status;
        this.subject = subject;
    }

    public List<CollaborateFieldDTO> getCollaborateFieldDTOs() {
        return collaborateFieldDTOs;
    }

    public void setCollaborateFieldDTOs(List<CollaborateFieldDTO> collaborateFieldDTOs) {
        this.collaborateFieldDTOs = collaborateFieldDTOs;
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
}
