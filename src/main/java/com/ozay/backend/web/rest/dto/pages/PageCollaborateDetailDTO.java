package com.ozay.backend.web.rest.dto.pages;

import com.ozay.backend.model.Collaborate;

import java.util.List;

/**
 * Created by naofumiezaki on 6/6/16.
 */
public class PageCollaborateDetailDTO {
    private Collaborate collaborate;
    private boolean archived;
    private boolean isCreator;
    private boolean firstEdit;
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

    public Collaborate getCollaborate() {
        return collaborate;
    }

    public void setCollaborate(Collaborate collaborate) {
        this.collaborate = collaborate;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
