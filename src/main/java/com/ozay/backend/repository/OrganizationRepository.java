package com.ozay.backend.repository;

import com.ozay.backend.domain.User;
import com.ozay.backend.model.Organization;
import com.ozay.backend.resultsetextractor.OrganizationSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naofumiezaki on 11/1/15.
 */
public class OrganizationRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Organization> findAllUserCanAccess(User user){
        String query = "SELECT * FROM organization o LEFT JOIN organization_user ou ON o.id = out.organization_id WHERE o.user_id = :userId OR ou.user_id = :userId GROUP BY o.id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", user.getId());
        return (List<Organization>) namedParameterJdbcTemplate.query(query, params, new OrganizationSetExtractor());
    }

    public Organization findOne(long organizationId){
        String query = "SELECT * FROM organization WHERE id = :organizationId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("organizationId", organizationId);
        List<Organization> organizations = (List<Organization>) namedParameterJdbcTemplate.query(query, params, new OrganizationSetExtractor());
        if(organizations.size() == 1){
            return organizations.get(0);
        } else {
            return null;
        }
    }

    public void create(Organization organization){
        String query = "INSERT INTO organization (user_id, name, created_date, address_1, address_2, phone, state, country, zip, created_by) " +
            "VALUES(:userId, :name, now(), :address1, :address2, :phone, :state, :country, :zip, :createdBy" +
            ")";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", organization.getUserId());
        params.addValue("name", organization.getName());
        params.addValue("address1", organization.getAddress1());
        params.addValue("address2", organization.getAddress2());
        params.addValue("phone", organization.getPhone());
        params.addValue("state", organization.getState());
        params.addValue("country", organization.getCountry());
        params.addValue("zip", organization.getZip());
        params.addValue("createdBy", organization.getCreatedBy());
        namedParameterJdbcTemplate.update(query,params);
    }

    public void update(Organization organization){
        String query = "UPDATE organization " +
            "SET user_id=:userId, " +
            "name=:name, " +
            "address_1=:address1," +
            "address_2=:address2," +
            "phone=:phone," +
            "state=:state," +
            "country=:country," +
            "zip=:zip, " +
            "modified_date=now(), " +
            "modified_by=:modifiedBy, " +
            "WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", organization.getUserId());
        params.addValue("name", organization.getName());
        params.addValue("address1", organization.getAddress1());
        params.addValue("address2", organization.getAddress2());
        params.addValue("phone", organization.getPhone());
        params.addValue("state", organization.getState());
        params.addValue("country", organization.getCountry());
        params.addValue("zip", organization.getZip());
        params.addValue("modifiedBy", organization.getModifiedBy());
        params.addValue("id", organization.getId());
        namedParameterJdbcTemplate.update(query,params);
    }

}
