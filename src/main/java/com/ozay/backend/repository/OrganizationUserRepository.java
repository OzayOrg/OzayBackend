package com.ozay.backend.repository;

import com.ozay.backend.domain.User;
import com.ozay.backend.model.Organization;
import com.ozay.backend.model.OrganizationPermission;
import com.ozay.backend.resultsetextractor.OrganizationSetExtractor;
import com.ozay.backend.security.SecurityUtils;
import com.ozay.backend.web.rest.dto.OrganizationUserDTO;
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


    public void create(OrganizationPermission organizationPermission){
        String query = "INSERT INTO organization_user (user_id, organization_id) VALUES(:userId, :organizationId)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", organizationPermission.getUserId());
        params.addValue("organizationId", organizationPermission.getOrganizationId());
        namedParameterJdbcTemplate.update(query,params);
    }

    public void deleteAllByUser(OrganizationUserDTO organizationUserDTO){
        String query="DELETE FROM organization_user WHERE user_id=:userId AND organization_id = :organizationId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", organizationUserDTO.getId());
        params.addValue("organizationId", organizationUserDTO.getOrganizationId());
        namedParameterJdbcTemplate.update(query,params);
    }
}
