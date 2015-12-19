package com.ozay.backend.resultsetextractor;

import com.ozay.backend.model.AccountInformation;
import com.ozay.backend.model.Member;
import com.ozay.backend.model.NotificationRecord;
import com.ozay.backend.model.NotificationTrack;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by naofumiezaki on 10/31/15.
 */
public class NotificationTrackResultSetExtractor implements ResultSetExtractor {

    public Object extractData(ResultSet resultSet) throws SQLException {
        List<NotificationTrack> list = new ArrayList<NotificationTrack>();
        while(resultSet.next()){
            NotificationTrack notificationTrack = new NotificationTrack();
            Member member = new Member();

            notificationTrack.setMemberId(resultSet.getLong("member_id"));
            notificationTrack.setNotificationId(resultSet.getLong("notification_id"));
            notificationTrack.setEmail(resultSet.getString("email"));
            notificationTrack.setNote(resultSet.getString("note"));
            notificationTrack.setSuccess(resultSet.getBoolean("success"));
           // notificationTrack.setTrack(resultSet.getBoolean("track"));
           // notificationTrack.setTrackComplete(resultSet.getBoolean("track_complete"));

            member.setFirstName(resultSet.getString("first_name"));
            member.setLastName(resultSet.getString("last_name"));
            member.setUnit(resultSet.getString("unit"));

            notificationTrack.setMember(member);
            list.add(notificationTrack);
        }

        return list;
    }
}

