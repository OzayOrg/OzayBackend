package com.ozay.backend.repository;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.CollaborateDate;
import com.ozay.backend.utility.DateTimeUtility;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.Timestamp;

/**
 * Created by naofumiezaki on 5/24/16.
 */
@Repository
public class CollaborateDateRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void create(CollaborateDate collaborateDate){
        String query = "INSERT INTO collaborate_date (collaborate_id, issue_date) VALUES(:collaborateId, :issueDate) RETURNING id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("collaborateId", collaborateDate.getCollaborateId());

        params.addValue("issueDate", DateTimeUtility.convertToTimeStamp(collaborateDate.getIssueDate()));
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
