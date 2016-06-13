package com.ozay.backend.resultsetextractor;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.CollaborateDate;
import com.ozay.backend.model.CollaborateMember;
import com.ozay.backend.model.Member;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by naofumiezaki on 5/24/16.
 */
public class CollaborateResultSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Collaborate> collaborateList = new ArrayList<Collaborate>();
        List<CollaborateDate> collaborateDateList = new ArrayList<CollaborateDate>();
        List<CollaborateMember> collaborateMemberList = new ArrayList<CollaborateMember>();
        Collaborate collaborate = null;

        Long previous = null; // For collaborate
        Long previous2 = null; // For collaborate date
        Long previous3 = null;
        CollaborateDate collaborateDate = null;
        Member member = null;
        CollaborateMember collaborateMember = null;

        while(resultSet.next()) {

            if(collaborate == null || resultSet.getLong("id") != previous){
                if(collaborate != null){
                    collaborateList.add(collaborate);
                    collaborate.setCollaborateDates(collaborateDateList);
                    collaborateDateList = new ArrayList<CollaborateDate>();
                }
                collaborate = new Collaborate();
                collaborate.setId(resultSet.getLong("id"));
                collaborate.setResponse(resultSet.getInt("response"));
                collaborate.setSubject(resultSet.getString("subject"));
                collaborate.setMessage(resultSet.getString("message"));
                collaborate.setBuildingId(resultSet.getLong("building_id"));
                collaborate.setCreatedDate(new DateTime(resultSet.getDate("created_date")));
                collaborate.setCreatedBy(resultSet.getLong("created_by"));
                collaborate.setStatus(resultSet.getInt("status"));

                if((Integer)resultSet.getObject("collaborate_date_id") == null){
                    collaborate.setCollaborateDateId(null);
                } else {
                    collaborate.setCollaborateDateId(resultSet.getLong("collaborate_date_id"));
                }
                collaborate.setCreatedDate(new DateTime(resultSet.getDate("created_date")));
                collaborate.setCollaborateDates(new ArrayList<CollaborateDate>());
            }
            if(collaborateDate == null || resultSet.getLong("cd_id") != previous2){
                if(collaborateDate != null){
                    collaborateDate.setCollaborateMembers(collaborateMemberList);
                    collaborateMemberList = new ArrayList<CollaborateMember>();
                }
                collaborateDate = new CollaborateDate();
                collaborateDate.setId(resultSet.getLong("cd_id"));
                collaborateDate.setCollaborateId(resultSet.getLong("cd_collaborate_id"));
                collaborateDate.setIssueDate(new DateTime(resultSet.getDate("issue_date")));
                collaborateDate.setCollaborateMembers(new ArrayList<CollaborateMember>());
                collaborateDateList.add(collaborateDate);
            }

            collaborateMember = new CollaborateMember();
            member = new Member();
            member.setId(resultSet.getLong("m_id"));
            member.setFirstName(resultSet.getString("first_name"));
            member.setLastName(resultSet.getString("last_name"));
            collaborateMember.setMember(member);
            collaborateMember.setCollaborateDateId(resultSet.getLong("collaborate_date_id"));
            Boolean b = resultSet.getBoolean("selected");

            if((Boolean)resultSet.getObject("selected") == null){
                collaborateMember.setSelected(null);
            } else {
                collaborateMember.setSelected(resultSet.getBoolean("selected"));
            }
            collaborateMemberList.add(collaborateMember);


            if((Date)resultSet.getObject("cm_modified_date") == null){
                collaborateMember.setModifiedDate(null);
            } else {
                collaborateMember.setModifiedDate(new DateTime(resultSet.getDate("cm_modified_date")));
            }

            previous = resultSet.getLong("id");
            previous2 = resultSet.getLong("cd_id");
        }
        if(collaborate != null){
            collaborateDate.setCollaborateMembers(collaborateMemberList);
            collaborate.setCollaborateDates(collaborateDateList);
            collaborateList.add(collaborate);
        }



        return collaborateList;
    }
}
