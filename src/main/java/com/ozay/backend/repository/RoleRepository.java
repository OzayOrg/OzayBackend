package com.ozay.backend.repository;

import com.ozay.backend.model.Role;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by naofumiezaki on 11/1/15.
 */
@Repository
public class RoleRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;





    public void create(Role role){
        String query="INSERT INTO role (building_id, name, organization_user_role, belong_to, sort_order) VALUES(:buildingId, :name, :organizationUserRole, :belongTo, (SELECT Max(sort_order) + 1 FROM role WHERE building_id = :buildingId)) RETURNING id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("buildingId", role.getBuildingId());
        params.addValue("name", role.getName());
        params.addValue("organizationUserRole", role.isOrganizationUserRole());
        params.addValue("belongTo", role.getBelongTo());
        namedParameterJdbcTemplate.update(query, params);
    }

    public void update(Role role){
        String query="UPDATE role SET building_id=:buildingId, name=:name, sort_order=:sortOrder, belong_to=:belongTo, organization_user_role= :organizationUserRole WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("buildingId", role.getBuildingId());
        params.addValue("name", role.getName());
        params.addValue("sortOrder", role.getSortOrder());
        params.addValue("organizationUserRole", role.isOrganizationUserRole());
        params.addValue("id", role.getId());
        params.addValue("belongTo", role.getBelongTo());
        namedParameterJdbcTemplate.update(query, params);
    }

    public void delete(Role role){
        String query = "DELETE FROM role WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", role.getId());
        namedParameterJdbcTemplate.update(query, params);
    }
}
