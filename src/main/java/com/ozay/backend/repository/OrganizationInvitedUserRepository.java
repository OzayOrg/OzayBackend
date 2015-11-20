package com.ozay.backend.repository;

import com.ozay.backend.model.InvitedUser;
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
public class OrganizationInvitedUserRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public void create(InvitedUser invitedUser){
        String query="INSERT INTO organization_invited_user (user_id, organization_id) VALUES(:userId, :organizationId)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", invitedUser.getId());
        params.addValue("organizationId", invitedUser.getOrganizationId());
        namedParameterJdbcTemplate.update(query,params);
    }

    public void delete(OrganizationUserDTO organizationUserDTO){
        String query="DELETE FROM organization_invited_user WHERE user_id=:userId AND organization_id = :organizationId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", organizationUserDTO.getId());
        params.addValue("organizationId", organizationUserDTO.getOrganizationId());
        namedParameterJdbcTemplate.update(query,params);
    }
}
