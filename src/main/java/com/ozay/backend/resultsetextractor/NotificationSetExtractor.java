package com.ozay.backend.resultsetextractor;

import com.ozay.backend.model.Building;
import com.ozay.backend.model.Notification;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naofumiezaki on 10/30/15.
 */
public class NotificationSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Notification> list = new ArrayList<Notification>();

        while(resultSet.next()){
            Notification notification = new Notification();
            notification.setId(resultSet.getLong("id"));
            notification.setSubject(resultSet.getString("subject"));
            notification.setNotice(resultSet.getString("notice"));
            notification.setCreatedBy(resultSet.getString("created_by"));
            notification.setBuildingId(resultSet.getLong("building_id"));
            list.add(notification);
        }
        return list;
    }

}
