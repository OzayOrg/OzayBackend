package com.ozay.backend.web.rest.dto.pages;

import com.ozay.backend.model.Collaborate;

import java.util.List;

/**
 * Created by naofumiezaki on 5/27/16.
 */
public class PageCollaborateRecordDTO {
    private Long numberOfRecords;
    private List<Collaborate> collaborates;

    public Long getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(Long numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

    public List<Collaborate> getCollaborates() {
        return collaborates;
    }

    public void setCollaborates(List<Collaborate> collaborates) {
        this.collaborates = collaborates;
    }
}
