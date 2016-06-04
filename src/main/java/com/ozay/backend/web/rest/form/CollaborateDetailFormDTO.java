package com.ozay.backend.web.rest.form;

import java.util.List;

/**
 * Created by naofumiezaki on 6/10/16.
 */
public class CollaborateDetailFormDTO {
    private Long collaborateId;
    private Integer response;
    private Long memberId;
    private List<CollaborateDetailFieldDTO> collaborateTrackField;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCollaborateId() {
        return collaborateId;
    }

    public void setCollaborateId(Long collaborateId) {
        this.collaborateId = collaborateId;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }

    public List<CollaborateDetailFieldDTO> getCollaborateTrackField() {
        return collaborateTrackField;
    }

    public void setCollaborateTrackField(List<CollaborateDetailFieldDTO> collaborateTrackField) {
        this.collaborateTrackField = collaborateTrackField;
    }
}
