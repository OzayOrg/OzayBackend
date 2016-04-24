package com.ozay.backend.repository;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.resultsetextractor.CollaborateSetExtractor;
import com.ozay.backend.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Timestamp;
import java.util.List;
/**
 * Created by RGV Krushnan on 25-03-2016.
 */
public class CollaborateRepository {
    @Autowired(required=true)
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Collaborate> findAllByBuildingId(Long buildingId, Long offset, String search){
        int limit = 20;
        offset = offset * limit;


        MapSqlParameterSource params = new MapSqlParameterSource();
        String partialQuery = "";
        if(search != null){
            params.addValue("unit", search);
            partialQuery = " AND subject=:unit ";
        }


        String query = "SELECT * FROM collaborate WHERE building_id = :buildingId " + partialQuery +  "order by created_date desc LIMIT :limit OFFSET :offset";

        params.addValue("buildingId", buildingId);
        params.addValue("limit", limit);
        params.addValue("offset", offset);


        return (List<Collaborate>)namedParameterJdbcTemplate.query(query, params, (ResultSetExtractor<Object>) new CollaborateSetExtractor());
    }

    public List<Collaborate> searchCollaborate(Long buildingId, String[] items){
        String query = "SELECT * FROM collaborate where building_id =:buildingId ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("buildingId", buildingId);

        String queryForList = "";
        for(int i = 0; i< items.length;i++){
            String param = "param" + i;
            params.addValue(param, items[i]);
            if(i != 0){
                queryForList += " OR ";
            }
            queryForList += " LOWER(notice) like '%"+items[i]+"%'" +
                " OR LOWER(subject) LIKE '%"+items[i]+"%'";
            //System.out.println(param[items(0)]);

        }
        if(items.length > 0){
            query += " AND (";
            query += queryForList;
            query += ")";
        }
        System.out.println(query);
        return (List<Collaborate>)namedParameterJdbcTemplate.query(query, params, (ResultSetExtractor<Object>) new CollaborateSetExtractor());
    };

    public Long countAllByBuildingId(Long buildingId, String search){

        String partialQuery = "";
        MapSqlParameterSource params = new MapSqlParameterSource();
        if(search != null){
            params.addValue("unit", search);
            partialQuery = " AND subject=:unit ";
        }

        String query = "SELECT COUNT(*) FROM collaborate WHERE building_id = :buildingId " + partialQuery + "";


        params.addValue("buildingId", buildingId);

        return namedParameterJdbcTemplate.queryForObject(query, params, Long.class);
    }

    public List<Collaborate> searchCollaborateWithLimit(Long buildingId, Long limit){

        String query = "SELECT * from collaborate WHERE id in(" +
            "SELECT MAX(id) " +
            "from collaborate " +
            "WHERE building_id=:buildingId " +
            "GROUP BY subject " +
            ") " +
            "ORDER BY surveyissueDate DESC " +
            "LIMIT :limit ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("buildingId", buildingId);
        params.addValue("limit", limit);

        return (List<Collaborate>)namedParameterJdbcTemplate.query(query, params, (ResultSetExtractor<Object>) new CollaborateSetExtractor());
    };

    public Collaborate findOne(Long collaborateId){

        String query = "SELECT * FROM collaborate WHERE id = :collaborateId";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("collaborateId", collaborateId);
        List<Collaborate> collaborates = (List<Collaborate>)namedParameterJdbcTemplate.query(query, params, (ResultSetExtractor<Object>) new CollaborateSetExtractor());
        if(collaborates.size() == 1){
            return collaborates.get(0);
        } else {
            return null;
        }
    }


    public void create(Collaborate collaborate){
        String query = "INSERT INTO collaborate (building_id, surveyissueDate, postedBy, subject) VALUES (:buildingId, :surveyissueDate, :postedBy, NOW(), :subject) RETURNING id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("buildingId", collaborate.getBuildingId());
        params.addValue("surveyissueDate", new Timestamp(collaborate.getSurveyissueDate().getMillis()));
        params.addValue("postedBy", SecurityUtils.getCurrentLogin());
        params.addValue("subject", collaborate.getSubject());
        Long id = namedParameterJdbcTemplate.queryForObject(query, params, Long.class);
        collaborate.setId(id);
    }


    }



