package com.ozay.backend.resultsetextractor;

import com.ozay.backend.model.Building;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naofumiezaki on 10/30/15.
 */
public class BuildingSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Building> list = new ArrayList<Building>();

        while(resultSet.next()){
            Building building = new Building();
            building.setId(resultSet.getLong("id"));
            building.setName(resultSet.getString("name"));
            building.setAddress("address");
            building.setApartment("apartment");
            building.setState("state");
            building.setZip("zip");
            building.setPhone("phone");
            building.setCreatedBy(resultSet.getLong("created_by"));
            building.setEmail(resultSet.getString("email"));
            building.setState(resultSet.getString("state"));
            building.setLastModifiedBy(resultSet.getLong("last_modified_by"));
            building.setTotalUnits(resultSet.getInt("total_units"));
            building.setOrganizationId(resultSet.getLong("organization_id"));
            list.add(building);

        }
        return list;
    }

}
