package com.ozay.backend.web.rest.dto.pages;

import com.ozay.backend.web.rest.dto.pages.partials.CollaborateDTO;
import com.ozay.backend.web.rest.dto.pages.partials.CollaborateFieldDTO;

import java.util.List;

/**
 * Created by naofumiezaki on 6/6/16.
 */
public class PageCollaborateDetailDTO {

    private boolean archived;
    private boolean isCreator;
    private boolean firstEdit;
    private CollaborateDTO collaborate;
    private CollaborateFieldDTO collaborateFieldDTO;

    private List<CollaborateFieldDTO> CollaborateFieldDTOs;

    public List<CollaborateFieldDTO> getCollaborateFieldDTOs() {
        return CollaborateFieldDTOs;
    }

    public CollaborateFieldDTO getCollaborateFieldDTO() {
        return collaborateFieldDTO;
    }

    public void setCollaborateFieldDTO(CollaborateFieldDTO collaborateFieldDTO) {
        this.collaborateFieldDTO = collaborateFieldDTO;
    }

    public void setCollaborateFieldDTOs(List<CollaborateFieldDTO> collaborateFieldDTOs) {
        CollaborateFieldDTOs = collaborateFieldDTOs;
    }

    private List<Long> selectedIds;

    public boolean isFirstEdit() {
        return firstEdit;
    }

    public void setFirstEdit(boolean firstEdit) {
        this.firstEdit = firstEdit;
    }

    public List<Long> getSelectedIds() {
        return selectedIds;
    }

    public boolean isCreator() {
        return isCreator;
    }

    public void setCreator(boolean creator) {
        isCreator = creator;
    }

    public void setSelectedIds(List<Long> selectedIds) {
        this.selectedIds = selectedIds;
    }

    public CollaborateDTO getCollaborate() {
        return collaborate;
    }

    public void setCollaborate(CollaborateDTO collaborate) {
        this.collaborate = collaborate;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
