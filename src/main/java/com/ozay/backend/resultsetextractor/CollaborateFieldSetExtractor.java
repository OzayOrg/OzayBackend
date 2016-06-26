package com.ozay.backend.resultsetextractor;

import com.ozay.backend.model.Building;
import com.ozay.backend.model.CollaborateField;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by naofumiezaki on 10/30/15.
 */
public class CollaborateFieldSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<CollaborateField> list = new ArrayList<CollaborateField>();

        while(resultSet.next()){
            CollaborateField collaborateField = new CollaborateField();
            collaborateField.setId(resultSet.getLong("id"));
            collaborateField.setCollaborateId(resultSet.getLong("collaborate_id"));
            collaborateField.setQuestion(resultSet.getString("question"));
            if((Date)resultSet.getObject("issue_date") == null){
                collaborateField.setIssueDate(null);
            } else {
                collaborateField.setIssueDate(new DateTime(resultSet.getDate("issue_date")));
            }

            list.add(collaborateField);

        }
        return list;
    }

}
