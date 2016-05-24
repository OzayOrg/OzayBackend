package com.ozay.backend.repository;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.resultsetextractor.CollaborateResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by naofumiezaki on 5/24/16.
 */
@Repository
public class CollaborateRepository {

    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Collaborate> findAllByBuilding(Long buildingId){
        String query = "SELECT c.*, cd.id as cd_id, cd.issue_date, m.id as m_id, m.first_name, m.last_name, m.unit FROM collaborate c INNER JOIN collaborate_date cd ON c.id = cd.collaborate_id INNER JOIN collaborate_date_member cdm ON cd.id = cdm.collaborate_date_id JOIN member m ON m.id = cdm.member_id AND m.deleted = false WHERE building_id = :buildingId";
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("buildingId", buildingId);
        return (List<Collaborate>)namedParameterJdbcTemplate.query(query, new CollaborateResultSetExtractor());
    }

    public void create(Collaborate collaborate){
        String query = "INSERT INTO collaborate (building_id, subject, response, created_by, created_date) VALUES (:buildingId, :subject, :response, :createdBy, now()) RETURNING id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("buildingId", collaborate.getBuildingId());
        params.addValue("subject", collaborate.getSubject());
        params.addValue("response", collaborate.getResponse());
        params.addValue("createdBy", collaborate.getCreatedBy());

        Long id = namedParameterJdbcTemplate.queryForObject(query, params, Long.class );
        collaborate.setId(id);
    }
    public void update(Collaborate collaborate){
        String query = "UPDATE collaborate SET subject=:subject, response=:response, modified_by, last_modified_date=NOW()) WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", collaborate.getId());

        params.addValue("subject", collaborate.getSubject());
        params.addValue("response", collaborate.getResponse());
        params.addValue("modifiedBy", collaborate.getModifiedBy());

        namedParameterJdbcTemplate.update(query, params );
    }
    public void delete(Collaborate collaborate){
        String query = "Delete collaborate WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        namedParameterJdbcTemplate.update(query, params);
    }
}
