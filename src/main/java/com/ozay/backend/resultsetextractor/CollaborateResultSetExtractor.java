package com.ozay.backend.resultsetextractor;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.CollaborateDate;
import com.ozay.backend.model.Member;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naofumiezaki on 5/24/16.
 */
public class CollaborateResultSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Collaborate> collaborateList = new ArrayList<Collaborate>();
        Collaborate collaborate = null;
        Long previous = null; // For collaborate
        Long previous2 = null; // For collaborate date
        CollaborateDate collaborateDate = null;

        while(resultSet.next()) {
            if(previous2 == null || previous2 != resultSet.getLong("cd_id")) {
                if (previous2 != null) {
                    collaborate.getCollaborateIssueDateList().add(collaborateDate);
                }
                collaborateDate = new CollaborateDate();
                collaborateDate.setMembers(new ArrayList<Member>());
                collaborate.setId(resultSet.getLong("cd_id"));
                collaborateDate.setCollaborateId(resultSet.getLong("id"));
                collaborateDate.setIssueDate(new DateTime(resultSet.getDate("issue_date")));
            }
            if(previous == null || previous != resultSet.getLong("id")) {
                if (previous != null) {
                    collaborateList.add(collaborate);
                }
                collaborate = new Collaborate();
                collaborate.setCollaborateIssueDateList(new ArrayList<CollaborateDate>());
                collaborate.setId(resultSet.getLong("id"));
                collaborate.setBuildingId(resultSet.getLong("building_id"));
                collaborate.setSubject(resultSet.getString("subject"));
                collaborate.setResponse(resultSet.getInt("response"));
            }

            previous = resultSet.getLong("id");
            previous2 = resultSet.getLong("cd_id");

            Member member = new Member();
            member.setId(resultSet.getLong("m_id"));
            member.setFirstName(resultSet.getString("first_name"));
            member.setLastName(resultSet.getString("last_name"));
            collaborateDate.getMembers().add(member);

        }

        if(collaborate != null){
            if(collaborateDate != null){
                collaborate.getCollaborateIssueDateList().add(collaborateDate);
            }
            collaborateList.add(collaborate);
        }
        return collaborateList;
    }
}
