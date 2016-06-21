package com.ozay.backend.repository;

import com.ozay.backend.model.CollaborateField;
import com.ozay.backend.utility.DateTimeUtility;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by naofumiezaki on 5/24/16.
 */
@Repository
public class CollaborateFieldRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void create(CollaborateField collaborateField){
        String query = "INSERT INTO collaborate_field (collaborate_id, issue_date) VALUES(:collaborateId, :issueDate) RETURNING id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("collaborateId", collaborateField.getCollaborateId());

        params.addValue("issueDate", DateTimeUtility.convertToTimeStamp(collaborateField.getIssueDate()));
        Long id =namedParameterJdbcTemplate.queryForObject(query, params, Long.class);
        collaborateField.setId(id);
    }

    public void delete(CollaborateField collaborateField){
        String query = "Delete collaborate_field WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", collaborateField.getId());
        namedParameterJdbcTemplate.update(query, params);
    }
}
