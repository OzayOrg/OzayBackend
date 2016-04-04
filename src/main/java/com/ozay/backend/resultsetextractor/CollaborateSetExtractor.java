package com.ozay.backend.resultsetextractor;


import com.ozay.backend.model.Collaborate;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RGV Krushnan on 25-03-2016.
 */
public class CollaborateSetExtractor {
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Collaborate> list = new ArrayList<Collaborate>();

        while(resultSet.next()){
            Collaborate collaborate = new Collaborate();
            collaborate.setId(resultSet.getLong("id"));
            collaborate.setSubject(resultSet.getString("subject"));
            collaborate.setpostedy(resultSet.getString("postedBy"));
            collaborate.setBuildingId(resultSet.getLong("building_id"));
            collaborate.setSurveyissueDate(new DateTime(resultSet.getDate("issueDate")));
            list.add(collaborate);
        }
        return list;
    }

}
