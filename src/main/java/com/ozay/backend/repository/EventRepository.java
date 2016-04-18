package com.ozay.backend.repository;

import com.ozay.backend.domain.User;
import com.ozay.backend.model.Event;
import com.ozay.backend.resultsetextractor.EventSetExtractor;
//import com.ozay.backend.resultsetextractor.OrganizationUserDTOSetExtractor;
import com.ozay.backend.security.SecurityUtils;
//import com.ozay.backend.web.rest.dto.OrganizationUserDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by naofumiezaki on 11/1/15.
 */
@Repository
public class EventRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

/*    public List<Organization> findAllUserCanAccess(User user){
        String query = "SELECT DISTINCT ON (o.id) * FROM organization o LEFT JOIN organization_user ou ON o.id = ou.organization_id WHERE o.user_id = :userId OR ou.user_id = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", user.getId());
        return (List<Organization>) namedParameterJdbcTemplate.query(query, params, new OrganizationSetExtractor());
    }

    public List<OrganizationUserDTO> findAllOrganizationActivatedUser(Long organizationId){
        String query = "SELECT u.*, ou.id as organization_user_id, ou.id as user_id, ou.activated as activated FROM jhi_user u INNER JOIN organization_user ou ON u.id = ou.user_id AND ou.activated = true WHERE ou.organization_id = :organizationId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("organizationId", organizationId);
        return (List<OrganizationUserDTO>) namedParameterJdbcTemplate.query(query, params, new OrganizationUserDTOSetExtractor());
    }

    public List<OrganizationUserDTO> findAllOrganizationInactivatedUser(Long organizationId){
        String query = "SELECT u.*, ou.id as organization_user_id, ou.id as user_id, ou.activated as activated FROM temp_user u INNER JOIN organization_user ou ON u.id = ou.temp_user_id AND ou.activated = false WHERE ou.organization_id = :organizationId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("organizationId", organizationId);
        return (List<OrganizationUserDTO>) namedParameterJdbcTemplate.query(query, params, new OrganizationUserDTOSetExtractor());
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
*/
    public void create(Event event){
        String query = "INSERT INTO events (id, name, date) " +
            "VALUES(:userId, :name, now()" +
            ")";

        SecurityUtils.getCurrentLogin();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", event.getId());
        params.addValue("name", event.getName());
        namedParameterJdbcTemplate.update(query,params);
    }

    public void update(Event event){
        String query = "UPDATE events " +
            "SET id=:userId, " +
            "name=:name, " +
            "date=now(), " +
            "WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", event.getName());
        params.addValue("id", event.getId());
        namedParameterJdbcTemplate.update(query,params);
    }

}
