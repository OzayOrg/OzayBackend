package com.ozay.backend.web.rest.dto.pages;

import java.util.List;

import com.ozay.backend.web.rest.dto.pages.partials.CollaborateDTO;

public class PageCollaborateResponseDTO {
	private long numberOfRecords;
    private List<CollaborateDTO> collaborateResponse;
    public List<CollaborateDTO> getCollaborateDTO() {
        return collaborateResponse;
    }

    public void setCollaborateDTO(List<CollaborateDTO> collaborateResponse) {
        this.collaborateResponse = collaborateResponse;
    }
    public long getNumberOfRecords(){
    	return numberOfRecords;
    }
    public void setNumberOfRecords(Long numberOfRecords){
    	this.numberOfRecords=numberOfRecords;
    }

}
