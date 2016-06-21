package com.ozay.backend.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by naofumiezaki on 5/24/16.
 */
@Repository
public class CollaborateMemberRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void create(Long collaborateId, Long collaborateFieldId, Long memberId){
        String query = "INSERT INTO collaborate_member (collaborate_id, collaborate_field_id, member_id) VALUES(:collaborateId,:collaborateFieldId, :memberId)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("collaborateId", collaborateId);
        params.addValue("memberId", memberId);
        params.addValue("collaborateFieldId", collaborateFieldId);
        namedParameterJdbcTemplate.update(query, params);
    }

    public void update(Long collaborateId, Long collaborateFieldId, Long memberId, Boolean selected){
        String query = "Update collaborate_member set selected=:selected, modified_date = NOW() WHERE collaborate_id=:collaborateId AND collaborate_field_id=:collaborateFieldId AND member_id =:memberId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("collaborateId", collaborateId);
        params.addValue("collaborateFieldId", collaborateFieldId);
        params.addValue("memberId", memberId);
        params.addValue("selected", selected);
        namedParameterJdbcTemplate.update(query, params);
    }


}
