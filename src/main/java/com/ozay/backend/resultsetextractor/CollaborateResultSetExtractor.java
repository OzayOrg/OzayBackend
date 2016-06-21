package com.ozay.backend.resultsetextractor;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.CollaborateField;
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
        List<CollaborateField> collaborateFieldList = new ArrayList<CollaborateField>();
        List<CollaborateMember> collaborateMemberList = new ArrayList<CollaborateMember>();
        Collaborate collaborate = null;

        Long previous = null; // For collaborate
        Long previous2 = null; // For collaborate date
        Long previous3 = null;
        CollaborateField collaborateField = null;
        Member member = null;
        CollaborateMember collaborateMember = null;

        while(resultSet.next()) {

            if(collaborate == null || resultSet.getLong("id") != previous){
                if(collaborate != null){
                    collaborateList.add(collaborate);
                    collaborate.setCollaborateFields(collaborateFieldList);
                    collaborateFieldList = new ArrayList<CollaborateField>();
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

                if((Integer)resultSet.getObject("collaborate_field_id") == null){
                    collaborate.setCollaborateFieldId(null);
                } else {
                    collaborate.setCollaborateFieldId(resultSet.getLong("collaborate_field_id"));
                }
                collaborate.setCreatedDate(new DateTime(resultSet.getDate("created_date")));
                collaborate.setCollaborateFields(new ArrayList<CollaborateField>());
            }
            if(collaborateField == null || resultSet.getLong("cd_id") != previous2){
                if(collaborateField != null){
                    collaborateField.setCollaborateMembers(collaborateMemberList);
                    collaborateMemberList = new ArrayList<CollaborateMember>();
                }
                collaborateField = new CollaborateField();
                collaborateField.setId(resultSet.getLong("cd_id"));
                collaborateField.setCollaborateId(resultSet.getLong("cd_collaborate_id"));
                collaborateField.setIssueDate(new DateTime(resultSet.getDate("issue_date")));
                collaborateField.setCollaborateMembers(new ArrayList<CollaborateMember>());
                collaborateFieldList.add(collaborateField);
            }

            collaborateMember = new CollaborateMember();
            member = new Member();
            member.setId(resultSet.getLong("m_id"));
            member.setFirstName(resultSet.getString("first_name"));
            member.setLastName(resultSet.getString("last_name"));
            collaborateMember.setMember(member);
            collaborateMember.setCollaborateFieldId(resultSet.getLong("collaborate_field_id"));
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
            collaborateField.setCollaborateMembers(collaborateMemberList);
            collaborate.setCollaborateFields(collaborateFieldList);
            collaborateList.add(collaborate);
        }



        return collaborateList;
    }
}
