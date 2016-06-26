package com.ozay.backend.web.rest.dto.pages.partials;

import org.joda.time.DateTime;

/**
 * Created by naofumiezaki on 6/24/16.
 */
public class CollaborateFieldDTO {
    private Long id;
    private Long collaborateId;
    private Long selectedCount;
    private DateTime issueDate;
    private String question;

    public CollaborateFieldDTO(){}

    public CollaborateFieldDTO(Long id, Long selectedCount, DateTime issueDate, String question) {
        this.id = id;
        this.selectedCount = selectedCount;
        this.issueDate = issueDate;
        this.question = question;
    }

    public Long getCollaborateId() {
        return collaborateId;
    }

    public void setCollaborateId(Long collaborateId) {
        this.collaborateId = collaborateId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(Long selectedCount) {
        this.selectedCount = selectedCount;
    }

    public DateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(DateTime issueDate) {
        this.issueDate = issueDate;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
