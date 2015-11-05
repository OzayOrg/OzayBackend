package com.ozay.backend.web.rest.dto.pages;

import com.ozay.backend.model.Building;

import java.util.List;

/**
 * Created by naofumiezaki on 11/4/15.
 */
public class PageOrganizationDetailDTO {
    private List<Building> buildings;

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }
}
