package com.ozay.backend.repository;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.CollaborateDate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by naofumiezaki on 5/24/16.
 */
@Repository
public class CollaborateDateRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void create(CollaborateDate collaborateDate){
        String query = "INSERT INTO collaborate_date (collaborate_id, issue_date, created_by, created_date) VALUES(:collaborateId, :issueDate, :createdBy, NOW() )";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("collaborateId", collaborateDate.getId());
        params.addValue("issueDate", collaborateDate.getId());
        params.addValue("createdBy", collaborateDate.getCreatedBy());
        Long id =namedParameterJdbcTemplate.queryForObject(query, params, Long.class);
        collaborateDate.setId(id);
    }

    public void delete(CollaborateDate collaborateDate){
        String query = "Delete collaborate_date WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", collaborateDate.getId());
        namedParameterJdbcTemplate.update(query, params);
    }
}
