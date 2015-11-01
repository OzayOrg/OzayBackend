package com.ozay.backend.repository;

import com.ozay.backend.model.Notification;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.Timestamp;

/**
 * Created by naofumiezaki on 10/31/15.
 */

@Repository
public class NotificationRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;





    public void create(Notification notification){
        String query = "INSERT INTO notification (building_id, notice, issue_date, created_by, created_date, subject) VALUES (:buildingId, :notice, :issueDate, :createdBy, NOW(), :subject) RETURNING id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("buildingId", notification.getBuildingId());
        params.addValue("notice", notification.getNotice());
        params.addValue("issueDate", new Timestamp(notification.getIssueDate().getMillis()));
        params.addValue("createdBy", notification.getCreatedBy());
        params.addValue("subject", notification.getSubject());

        namedParameterJdbcTemplate.queryForObject(query, params, Long.class);
    }
}
