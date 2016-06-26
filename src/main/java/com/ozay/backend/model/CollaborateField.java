package com.ozay.backend.model;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by naofumiezaki on 5/24/16.
 */
public class CollaborateField extends Model{
    private Long id;
    private Long collaborateId;
    private DateTime issueDate;
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    private List<CollaborateMember> collaborateMembers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollaborateId() {
        return collaborateId;
    }

    public void setCollaborateId(Long collaborateId) {
        this.collaborateId = collaborateId;
    }

    public DateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(DateTime issueDate) {
        this.issueDate = issueDate;
    }

    public List<CollaborateMember> getCollaborateMembers() {
        return collaborateMembers;
    }

    public void setCollaborateMembers(List<CollaborateMember> collaborateMembers) {
        this.collaborateMembers = collaborateMembers;
    }
    @Override
    public String toString() {
        return "CollaborateField{" +
            "id='" + id + '\'' +
            "collaborateId='" + collaborateId + '\'' +
            "issueDate='" + issueDate + '\'' +
            "member='" + collaborateMembers + '\'' +
            "}";
    }
}
