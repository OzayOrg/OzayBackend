package com.ozay.backend.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by naofumiezaki on 5/24/16.
 */
@Repository
public class CollaborateDateMemberRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void create(Long collaborateDateId, Long memberId){
        String query = "INSERT INTO collaborate_date_member (collaborate_date_id, member_id) VALUES(:collaborateDateId AND member_id=:memberId)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("collaborateDateId", collaborateDateId);
        params.addValue("memberId", memberId);
        namedParameterJdbcTemplate.update(query, params);
    }
    public void delete(Long collaborateDateId, Long memberId){
        String query = "Delete collaborate_date_member WHERE collaborate_date_id:collaborateDateId AND member_id=:memberId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("collaborateDateId", collaborateDateId);
        params.addValue("memberId", memberId);
        namedParameterJdbcTemplate.update(query, params);
    }
}
