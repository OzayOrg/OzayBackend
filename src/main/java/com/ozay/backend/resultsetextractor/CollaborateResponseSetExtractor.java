package com.ozay.backend.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.CollaborateField;
import com.ozay.backend.model.CollaborateMember;
import com.ozay.backend.model.Member;

public class CollaborateResponseSetExtractor {
	public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Collaborate> collaborateList = new ArrayList<Collaborate>();
        List<CollaborateField> collaborateFieldList = new ArrayList<CollaborateField>();
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
                collaborate.setDisplayUntil(new DateTime(resultSet.getDate("display_until")));

                if((Integer)resultSet.getObject("collaborate_field_id") == null){
                    collaborate.setCollaborateFieldId(null);
                } else {
                    collaborate.setCollaborateFieldId(resultSet.getLong("collaborate_fieldId"));
                }
                collaborate.setCreatedDate(new DateTime(resultSet.getDate("created_date")));
                collaborate.setCollaborateFields(new ArrayList<CollaborateField>());
            }
            if(collaborateField == null || resultSet.getLong("id") != previous2){
                collaborateField = new CollaborateField();
                collaborateField.setId(resultSet.getLong("id"));
                collaborateField.setCollaborateId(resultSet.getLong("id"));
                if((Date)resultSet.getObject("issue_date") == null){
                    collaborateField.setIssueDate(null);
                } else {
                    collaborateField.setIssueDate(new DateTime(resultSet.getDate("issue_date")));
                }
                collaborateField.setQuestion(resultSet.getString("question"));

                collaborateField.setCollaborateMembers(new ArrayList<CollaborateMember>());
                collaborateFieldList.add(collaborateField);
            }

            previous = resultSet.getLong("id");
            previous2 = resultSet.getLong("id");
        }
        if(collaborate != null){
            collaborate.setCollaborateFields(collaborateFieldList);
            collaborateList.add(collaborate);
        }



        return collaborateList;
    }

}
