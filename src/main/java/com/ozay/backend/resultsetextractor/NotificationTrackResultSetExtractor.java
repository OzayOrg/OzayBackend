package com.ozay.backend.resultsetextractor;

import com.ozay.backend.model.*;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by naofumiezaki on 10/31/15.
 */
public class NotificationTrackResultSetExtractor implements ResultSetExtractor {

    public Object extractData(ResultSet resultSet) throws SQLException {
        List<NotificationRecord> list = new ArrayList<NotificationRecord>();
        while(resultSet.next()){
            NotificationRecord notificationRecord = new NotificationRecord();
            Member member = new Member();
            Notification notification = new Notification();
            Building building =new Building();

            notificationRecord.setMemberId(resultSet.getLong("member_id"));
            notificationRecord.setNotificationId(resultSet.getLong("notification_id"));
            notificationRecord.setEmail(resultSet.getString("email"));
            notificationRecord.setNote(resultSet.getString("note"));
            notificationRecord.setSuccess(resultSet.getBoolean("success"));
            notificationRecord.setCommented(resultSet.getBoolean("commented"));
            notification.setCreatedDate(new DateTime(resultSet.getDate("created_date")));
            notification.setSubject(resultSet.getString("subject"));
            notificationRecord.setTrackComplete(resultSet.getBoolean("track_complete"));
           // notificationRecord.setTrack(resultSet.getBoolean("track"));
           // notificationRecord.setTrackComplete(resultSet.getBoolean("track_complete"));

            if(resultSet.getDate("track_completed_date") != null){
                notificationRecord.setTrackCompletedDate(new DateTime(resultSet.getDate("track_completed_date")));
            }

            member.setFirstName(resultSet.getString("first_name"));
            member.setLastName(resultSet.getString("last_name"));
            member.setUnit(resultSet.getString("unit"));

            building.setName(resultSet.getString("name"));
            notificationRecord.setMember(member);
            notificationRecord.setNotification(notification);
            notificationRecord.setBuilding(building);
            list.add(notificationRecord);
        }

        return list;
    }
}

