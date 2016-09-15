package com.ozay.backend.repository;

import com.ozay.backend.model.CollaborateField;
import com.ozay.backend.resultsetextractor.CollaborateFieldSetExtractor;
import com.ozay.backend.utility.DateTimeUtility;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by naofumiezaki on 5/24/16.
 */
@Repository
public class CollaborateFieldRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CollaborateField findAllById(Long id){
        String query = "SELECT * FROM collaborate_field WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<CollaborateField> list = (List<CollaborateField>)  namedParameterJdbcTemplate.query(query, params,new CollaborateFieldSetExtractor());
        if(list.size() == 1){
            return list.get(0);
        } else {
            return null;
        }

    }

    public List<CollaborateField> findAllByCollaborateId(Long collaborateId){
        String query = "SELECT * FROM collaborate_field WHERE collaborate_id = :collaborateId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("collaborateId", collaborateId);
        return (List<CollaborateField>)  namedParameterJdbcTemplate.query(query, params,new CollaborateFieldSetExtractor());
    }

    public void create(CollaborateField collaborateField){
        String query = "INSERT INTO collaborate_field (collaborate_id, issue_date, question) VALUES(:collaborateId, :issueDate, :question) RETURNING id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("collaborateId", collaborateField.getCollaborateId());
        DateTime d = null;
        if(collaborateField.getIssueDate() != null){
            d = collaborateField.getIssueDate().withMillisOfSecond(0);
        }
        params.addValue("issueDate", DateTimeUtility.convertToTimeStamp(d));
        params.addValue("question", collaborateField.getQuestion());
        
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
