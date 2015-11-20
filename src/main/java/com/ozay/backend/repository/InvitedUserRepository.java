package com.ozay.backend.repository;

import com.ozay.backend.model.InvitedUser;
import com.ozay.backend.resultsetextractor.InvitedUserPermissionSetExtractor;
import com.ozay.backend.resultsetextractor.InvitedUserSetExtractor;
import com.ozay.backend.security.SecurityUtils;
import com.ozay.backend.service.util.RandomUtil;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by naofumiezaki on 11/19/15.
 */
@Repository
public class InvitedUserRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<InvitedUser>findAllByOrganizationId(Long organizationId){
        String query = "SELECT * FROM invited_user WHERE organization_id = :organizationId AND activated = false";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("organizationId", organizationId);
        return (List<InvitedUser>)namedParameterJdbcTemplate.query(query, params, new InvitedUserSetExtractor());
    }

    public InvitedUser findOne(Long id){
        String query = "SELECT * FROM invited_user iu LEFT JOIN organization_invited_user oiu WHERE id=:id AND iu.activated = false";
        MapSqlParameterSource params = new MapSqlParameterSource();

        List<InvitedUser> invitedUsers = (List<InvitedUser>)namedParameterJdbcTemplate.query(query, params, new InvitedUserPermissionSetExtractor());
        if(invitedUsers.size() == 1){
            return invitedUsers.get(0);
        } else {
            return null;
        }
    }

    public void create(InvitedUser invitedUser){
        String query = "INSERT INTO invited_user (first_name, last_name, email, activation_key, activated, organization_id, created_by, created_date, last_modified_by, last_modified_date) VALUES (:firstName, :lastName, :email, :activationKey, :activated, :organizationId, :createdBy, NOW(), :createdBy, NOW()) RETURNING id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("firstName", invitedUser.getFirstName());
        params.addValue("lastName", invitedUser.getLastName());
        params.addValue("email", invitedUser.getEmail());
        params.addValue("organizationId", invitedUser.getOrganizationId());
        params.addValue("activationKey", RandomUtil.generateResetKey());
        params.addValue("activated", invitedUser.isActivated());
        params.addValue("createdBy", SecurityUtils.getCurrentLogin());
        Long id = namedParameterJdbcTemplate.queryForObject(query,params, Long.class);
        System.out.println(id);
        invitedUser.setId(id);
    }

    public void update(InvitedUser invitedUser){
        String query = "UPDATE invited_user SET first_name=:firstName, last_name = :lastName, email=:email, activated=:activated, last_modified_by=:modifiedBy, last_modified_date=NOW()";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("firstName", invitedUser.getFirstName());
        params.addValue("lastName", invitedUser.getLastName());
        params.addValue("email", invitedUser.getEmail());
        params.addValue("activated", invitedUser.isActivated());
        params.addValue("modifiedBy", SecurityUtils.getCurrentLogin());
        namedParameterJdbcTemplate.update(query,params);
    }
}
