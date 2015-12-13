package com.ozay.backend.repository;

import com.ozay.backend.model.NotificationRecord;
import com.ozay.backend.resultsetextractor.NotificationRecordResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by naofumiezaki on 11/24/15.
 */
@Repository
public class NotificationRecordRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<NotificationRecord> findAllByNotificationId(Long notificationId){

        String query = "SELECT nr.*, m.first_name, m.last_name, m.unit FROM notification_record nr INNER JOIN member m ON nr.member_id = m.id INNER JOIN notification n ON nr.notification_id = n.id WHERE nr.notification_id = :notificationId ORDER BY n.created_date DESC";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("notificationId", notificationId);

        return (List<NotificationRecord>)namedParameterJdbcTemplate.query(query, params, new NotificationRecordResultSetExtractor());
    }

    public Long countAllByNotificationId(Long buildingId){
        String query = "SELECT COUNT(*) FROM notification_record nr JOIN notification n ON n.id = nr.notification_id WHERE building_id = :buildingId";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("buildingId", buildingId);

        return namedParameterJdbcTemplate.queryForObject(query, params, Long.class);
    }

    public void create(NotificationRecord notificationRecord){
        String query = "INSERT INTO notification_record (notification_id, member_id, success, email, note, track, complete) VALUES(:notificationId, :memberId, :success, :email, :note, :track, :complete)";
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("notificationId", notificationRecord.getNotificationId());
        params.addValue("memberId", notificationRecord.getMemberId());
        params.addValue("success", notificationRecord.isSuccess());
        params.addValue("note", notificationRecord.getNote());
        params.addValue("email", notificationRecord.getEmail());
        params.addValue("track", notificationRecord.isTrack());
        params.addValue("complete", notificationRecord.isComplete());

        namedParameterJdbcTemplate.update(query, params);
    }
}
