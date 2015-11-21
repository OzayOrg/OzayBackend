package com.ozay.backend.repository;

import com.ozay.backend.model.OrganizationUser;
import com.ozay.backend.resultsetextractor.OrganizationUserSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by naofumiezaki on 11/1/15.
 */
@Repository
public class OrganizationUserRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrganizationUser findOne(Long id){
        String query = "SELECT * FROM organization_user WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List <OrganizationUser> organizationUsers = (List<OrganizationUser>)namedParameterJdbcTemplate.query(query, params, new OrganizationUserSetExtractor());
        if(organizationUsers.size() == 1){
            return organizationUsers.get(0);
        } else {
            return null;
        }
    }

    public void create(OrganizationUser organizationUser){
        String query = "INSERT INTO organization_user (user_id, organization_id, activated) VALUES(:userId, :organizationId, :activated) RETURNING id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", organizationUser.getUserId());
        params.addValue("organizationId", organizationUser.getOrganizationId());
        params.addValue("activated", organizationUser.isActivated());
        Long id = namedParameterJdbcTemplate.queryForObject(query,params, Long.class);
        organizationUser.setId(id);
    }

    public void deleteAllByUser(Long id){
        String query="DELETE FROM organization_user WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(query,params);
    }
}
