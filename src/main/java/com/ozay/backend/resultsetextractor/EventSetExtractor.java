package com.ozay.backend.resultsetextractor;

import com.ozay.backend.model.Event;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naofumiezaki on 11/1/15.
 */
public class EventSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Event> events = new ArrayList<Event>();

        while(resultSet.next()){
            Event event = new Event();
            event.setId(resultSet.getLong("id"));
            event.setName(resultSet.getString("name"));
            events.add(event);
        }

        return events;
    }
}
