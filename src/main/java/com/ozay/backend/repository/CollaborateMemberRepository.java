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

    public void create(Long collaborateId, Long collaborateDateId, Long memberId){
        String query = "INSERT INTO collaborate_member (collaborate_id, collaborate_date_id, member_id) VALUES(:collaborateId,:collaborateDateId, :memberId)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("collaborateId", collaborateId);
        params.addValue("memberId", memberId);
        params.addValue("collaborateDateId", collaborateDateId);
        namedParameterJdbcTemplate.update(query, params);
    }

    public void update(Long collaborateId, Long collaborateDateId, Long memberId, Boolean selected){
        String query = "Update collaborate_member set selected=:selected WHERE collaborate_id=:collaborateId AND collaborate_date_id=:collaborateDateId AND member_id =:memberId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("collaborateId", collaborateId);
        params.addValue("collaborateDateId", collaborateDateId);
        params.addValue("memberId", memberId);
        params.addValue("selected", selected);
        namedParameterJdbcTemplate.update(query, params);
    }


}
