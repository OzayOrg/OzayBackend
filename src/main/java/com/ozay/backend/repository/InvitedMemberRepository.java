package com.ozay.backend.repository;

import com.ozay.backend.model.InvitedMember;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by naofumiezaki on 10/30/15.
 */

@Repository
public class InvitedMemberRepository {

    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public InvitedMember findOne(String activationKey){
        String query = "SELECT * FROM invited_member WHERE activation_key = :activation_key and activated = false";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("activation_key", activationKey);
        return null;
    }

}

