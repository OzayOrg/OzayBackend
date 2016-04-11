package com.ozay.backend.web.rest.dto.pages;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.CollaborateRecord;

import java.util.List;

/**
 * Created by RGV Krushnan on 06-04-2016.
 */
public class PageCollaborateRecordDetailDTO {
    private Collaborate collaborate;
    private List<CollaborateRecord> collaborateRecords;

    public Collaborate getCollaborate() {
        return collaborate;
    }

    public void setCollaborate(Collaborate collaborate) {
        this.collaborate = collaborate;
    }

    public List<CollaborateRecord> getCollaborateRecords() {
        return collaborateRecords;
    }

    public void setCollaborateRecords(List<CollaborateRecord> collaborateRecords) {
        this.collaborateRecords = collaborateRecords;
    }

}
