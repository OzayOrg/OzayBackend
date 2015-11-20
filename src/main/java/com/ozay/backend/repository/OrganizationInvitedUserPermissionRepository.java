package com.ozay.backend.repository;

import com.ozay.backend.model.OrganizationPermission;
import com.ozay.backend.web.rest.dto.OrganizationUserDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by naofumiezaki on 11/18/15.
 */
@Repository
public class OrganizationInvitedUserPermissionRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public void create(OrganizationPermission organizationPermission){
        String query="INSERT INTO organization_invited_user_permission (user_id, permission_id, organization_id) VALUES(:userId, :permissionId, :organizationId)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", organizationPermission.getUserId());
        params.addValue("permissionId", organizationPermission.getPermissionId());
        params.addValue("organizationId", organizationPermission.getOrganizationId());
        namedParameterJdbcTemplate.update(query,params);
    }

    public void deleteAllByUser(OrganizationUserDTO organizationUserDTO){
        String query="DELETE FROM organization_invited_user_permission WHERE user_id=:userId AND organization_id = :organizationId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", organizationUserDTO.getId());
        params.addValue("organizationId", organizationUserDTO.getOrganizationId());
        namedParameterJdbcTemplate.update(query,params);
    }
}
