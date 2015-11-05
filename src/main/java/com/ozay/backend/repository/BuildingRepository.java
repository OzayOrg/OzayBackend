package com.ozay.backend.repository;

import com.ozay.backend.domain.User;
import com.ozay.backend.model.Building;
import com.ozay.backend.resultsetextractor.BuildingSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by naofumiezaki on 10/30/15.
 */

@Repository
public class BuildingRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Building> findAll(){
        String query = "SELECT * FROM building order by id";
        return (List<Building>)namedParameterJdbcTemplate.query(query, new BuildingSetExtractor(){});
    }

    public List<Building> findAllUserCanAccess(User user){
        String query = "SELECT b.* FROM building b LEFT JOIN organization o ON o.id = b.organization_id LEFT JOIN member m ON b.id = m.building_id LEFT JOIN subscription s ON s.id = o.user_id WHERE s.user_id = :userId OR m.user_id = :userId GROUP BY b.id ORDER BY b.id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", user.getId());
        return (List<Building>)namedParameterJdbcTemplate.query(query, params, new BuildingSetExtractor(){});
    }

    public List<Building> findAllOrganizationBuildings(long organizationId){
        String query = "SELECT * FROM building WHERE organization_id = :organizationId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("organizationId", organizationId);
        return (List<Building>)namedParameterJdbcTemplate.query(query, params, new BuildingSetExtractor(){});
    }

    public Building getBuilding(long id){
        String query = "SELECT * FROM building WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<Building> list =  (List<Building>)namedParameterJdbcTemplate.query(query, params, new BuildingSetExtractor(){});

        if(list.size() == 1){
            return list.get(0);
        }
        else {
            return null;
        }
    }


    public void create(Building building){

        String insert = "INSERT INTO building(name, organization_id, email, address, apartment, state, zip, phone, total_units, created_by, created_date) VALUES (:name, :organizationId, :email, :address, :apartment, :state, :zip, :phone, :totalUnits,:createdBy, now()) RETURNING id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", building.getName());
        params.addValue("organizationId", building.getOrganizationId());
        params.addValue("email", building.getEmail());
        params.addValue("address", building.getAddress());
        params.addValue("apartment", building.getApartment());
        params.addValue("state", building.getState());
        params.addValue("zip", building.getZip());
        params.addValue("phone", building.getPhone());
        params.addValue("totalUnits", building.getTotalUnits());
        params.addValue("createdBy", building.getCreatedBy());

        long id = namedParameterJdbcTemplate.queryForObject(insert, params, Integer.class );
        building.setId(id);
    }

    public void update(Building building){

        String query = "UPDATE building SET name =:name, organization_id = :organizationId, email =:email, address= :address, apartment = :apartment, state=:state, zip=:zip, phone=:phone, total_units=:totalUnits, last_modified_by =:modifiedBy, last_modified_date=now() WHERE id=:id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", building.getName());
        params.addValue("organizationId", building.getOrganizationId());
        params.addValue("email", building.getEmail());
        params.addValue("address", building.getAddress());
        params.addValue("apartment", building.getApartment());
        params.addValue("state", building.getState());
        params.addValue("zip", building.getZip());
        params.addValue("phone", building.getPhone());
        params.addValue("totalUnits", building.getTotalUnits());
        params.addValue("modifiedBy", building.getLastModifiedBy());
        params.addValue("id", building.getId());

        namedParameterJdbcTemplate.update(query, params);
    }
}

