package com.ozay.backend.repository;

import com.ozay.backend.model.InvitedUser;
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
        String query = "SELECT * FROM invited_user WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<InvitedUser>  invitedUsers = (List<InvitedUser>)namedParameterJdbcTemplate.query(query, params, new InvitedUserSetExtractor());
        if(invitedUsers.size() == 1){
            return invitedUsers.get(0);
        } else{
            return null;
        }
    }

    public InvitedUser findOneByActivationKey(String activationKey){
        String query = "SELECT * FROM invited_user WHERE activation_key=:activationKey";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("activationKey", activationKey);
        List<InvitedUser>  invitedUsers = (List<InvitedUser>)namedParameterJdbcTemplate.query(query, params, new InvitedUserSetExtractor());
        if(invitedUsers.size() == 1){
            return invitedUsers.get(0);
        } else{
            return null;
        }
    }

    public List<InvitedUser>findAllByOrganizationIdAndEmail(Long organizationId, String email){
        String query = "SELECT * FROM invited_user iu INNER JOIN organization_user ou ON iu.id = ou.user_id AND ou.activated = false WHERE ou.organization_id = :organizationId AND email=:email AND iu.activated = false";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("organizationId", organizationId);
        params.addValue("email", email);
        return (List<InvitedUser>)namedParameterJdbcTemplate.query(query, params, new InvitedUserSetExtractor());
    }

    public void create(InvitedUser invitedUser){
        String query = "INSERT INTO invited_user (first_name, last_name, email, activation_key, activated, created_by, created_date, last_modified_by, last_modified_date) VALUES (:firstName, :lastName, :email, :activationKey, :activated, :createdBy, NOW(), :createdBy, NOW()) RETURNING id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("firstName", invitedUser.getFirstName());
        params.addValue("lastName", invitedUser.getLastName());
        params.addValue("email", invitedUser.getEmail());
        params.addValue("activationKey", RandomUtil.generateResetKey());
        params.addValue("activated", invitedUser.isActivated());
        params.addValue("createdBy", SecurityUtils.getCurrentLogin());
        Long id = namedParameterJdbcTemplate.queryForObject(query,params, Long.class);
        invitedUser.setId(id);
    }

    public void update(InvitedUser invitedUser){
        String query = "UPDATE invited_user SET first_name=:firstName, last_name = :lastName, email=:email, activated=:activated, last_modified_by=:modifiedBy, last_modified_date=NOW() WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("firstName", invitedUser.getFirstName());
        params.addValue("lastName", invitedUser.getLastName());
        params.addValue("email", invitedUser.getEmail());
        params.addValue("activated", invitedUser.isActivated());
        params.addValue("modifiedBy", SecurityUtils.getCurrentLogin());
        params.addValue("id", invitedUser.getId());

        namedParameterJdbcTemplate.update(query,params);
    }
}
