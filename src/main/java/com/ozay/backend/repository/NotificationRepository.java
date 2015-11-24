package com.ozay.backend.repository;

import com.ozay.backend.model.Notification;
import com.ozay.backend.resultsetextractor.NotificationSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by naofumiezaki on 10/31/15.
 */

@Repository
public class NotificationRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Notification> searchNotificationWithLimit(Long buildingId, Long limit){

        String query = "SELECT * from notification WHERE id in(" +
            "SELECT MAX(id) " +
            "from notification " +
            "WHERE building_id=:buildingId " +
            "GROUP BY subject " +
            ") " +
            "ORDER BY created_date DESC " +
            "LIMIT :limit ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("buildingId", buildingId);
        params.addValue("limit", limit);

        return (List<Notification>)namedParameterJdbcTemplate.query(query, params, new NotificationSetExtractor());
    };


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
