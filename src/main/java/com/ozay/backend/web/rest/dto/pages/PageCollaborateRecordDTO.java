package com.ozay.backend.web.rest.dto.pages;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.web.rest.dto.pages.partials.CollaborateRecordDTO;

import java.util.List;

/**
 * Created by naofumiezaki on 5/27/16.
 */
public class PageCollaborateRecordDTO {
    private long numberOfRecords;
    private List<CollaborateRecordDTO> collaborateRecordDTOs;

    public List<CollaborateRecordDTO> getCollaborateRecordDTOs() {
        return collaborateRecordDTOs;
    }

    public void setCollaborateRecordDTOs(List<CollaborateRecordDTO> collaborateRecordDTOs) {
        this.collaborateRecordDTOs = collaborateRecordDTOs;
    }

    public long getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(long numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

}
