package com.ozay.backend.resultsetextractor;

import com.ozay.backend.model.Member;
import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.CollaborateRecord;
import org.joda.time.DateTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RGV Krushnan on 25-03-2016.
 */
public class CollaborateTrackResultSetExtractor {

    public Object extractData(ResultSet resultSet) throws SQLException {
        List<CollaborateRecord> list = new ArrayList<CollaborateRecord>();
        while(resultSet.next()) {
            CollaborateRecord collaborateRecord = new CollaborateRecord();
            Member member = new Member();
            Collaborate collaborate = new Collaborate();

            collaborateRecord.setMemberId(resultSet.getLong("member_id"));
            collaborateRecord.setCollaborateId(resultSet.getLong("collaborate_id"));
            collaborateRecord.setSurveySent(resultSet.getBoolean("success"));
            collaborate.setSurveyissueDate(new DateTime(resultSet.getDate("created_date")));
            collaborate.setSubject(resultSet.getString("subject"));

            member.setFirstName(resultSet.getString("first_name"));
            member.setLastName(resultSet.getString("last_name"));
            member.setUnit(resultSet.getString("unit"));

            collaborateRecord.setMember(member);
            list.add(collaborateRecord);
        }

        return list;
    }
}
