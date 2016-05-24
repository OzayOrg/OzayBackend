package com.ozay.backend.model;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by naofumiezaki on 5/24/16.
 */
public class CollaborateDate extends Model{
    private Long id;
    private Long collaborateId;
    private DateTime issueDate;
    private List<Member> members;

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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
