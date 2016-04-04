package com.ozay.backend.repository;

import com.ozay.backend.model.CollaborateRecord;
import com.ozay.backend.resultsetextractor.CollaborateRecordResultSetExtractor;
import com.ozay.backend.resultsetextractor.CollaborateTrackResultSetExtractor;
import com.ozay.backend.service.MailService;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by RGV Krushnan on 25-03-2016.
 */
public class CollaborateRecordRepository {

    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Inject
    MailService mailService;


    public List<CollaborateRecord> findAllByCollaborateId(Long collaborateId){

        String query = "SELECT nr.*, m.first_name, m.last_name, m.unit, n.track FROM collaborate_record nr INNER JOIN member m ON nr.member_id = m.id INNER JOIN collaborate n ON nr.collaborate_id = n.id WHERE nr.collaborate_id = :collaborateId ORDER BY n.created_date DESC";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("collaborateId", collaborateId);

        return (List<CollaborateRecord>)namedParameterJdbcTemplate.query(query, params, (ResultSetExtractor<Object>) new CollaborateRecordResultSetExtractor());
    }

    public List<CollaborateRecord> findAllTrackedByBuildingId(Long buildingId, Long offset, String search){
        int limit = 10;
        offset = offset * limit;
        MapSqlParameterSource params = new MapSqlParameterSource();
        String partialQuery = "";
        if(search != null){
            params.addValue("unit", search);
            partialQuery = " AND (lower(m.unit)=lower(:unit) or lower(m.first_name) = lower(:unit) or lower(m.last_name) = lower(:unit))  ";
        }
        String query = "SELECT n.created_date, n.subject, nr.*, m.first_name, m.last_name, m.unit, n.track FROM collaborate_record nr INNER JOIN collaborate n ON nr.collaborate_id = n.id AND n.track = true INNER JOIN member m ON nr.member_id = m.id " + partialQuery + " WHERE n.building_id = :buildingId ORDER BY nr.track_complete, n.created_date DESC LIMIT :limit OFFSET :offset";


        params.addValue("buildingId", buildingId);
        params.addValue("limit", limit);
        params.addValue("offset", offset);

        return (List<CollaborateRecord>)namedParameterJdbcTemplate.query(query, params, (ResultSetExtractor<Object>) new CollaborateTrackResultSetExtractor());
    }

    public Long countAllByCollaborateId(Long buildingId, String search){
        String partialQuery = "";
        MapSqlParameterSource params = new MapSqlParameterSource();
        if(search != null){
            params.addValue("unit", search);
            partialQuery = " AND m.unit=:unit ";
        }
        String query = "SELECT COUNT(*) FROM collaborate_record c_r INNER JOIN collaborate c ON c_r.collaborate_id = c.id AND c.track = true INNER JOIN member m ON c_r.member_id = m.id " + partialQuery + " WHERE c.building_id = :buildingId";

        params.addValue("buildingId", buildingId);

        return namedParameterJdbcTemplate.queryForObject(query, params, Long.class);
    }

    public void create(CollaborateRecord collaborateRecord){
        String query = "INSERT INTO collaborate_record (collaborate_id, member_id, sent) VALUES(:collaborateId, :memberId, :success)";
        MapSqlParameterSource params = new MapSqlParameterSource();

    //    params.addValue("collaborateId", collaborateRecord.getCollaborateId());
        params.addValue("memberId", collaborateRecord.getMemberId());
       // getting error : has to be taken care
        // params.addValue("sent", collaborateRecord.setSurveySent());
        //params.addValue("email", collaborateRecord.getEmail());

        namedParameterJdbcTemplate.update(query, params);
    }

    public void update(CollaborateRecord collaborateRecord){
        String query = "UPDATE collaborate_record SET track_complete=:trackComplete, track_completed_date=(select now()) WHERE collaborate_id=:collaborateId AND member_id=:memberId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        //params.addValue("collaborateId", collaborateRecord.getCollaborateId());
        params.addValue("memberId", collaborateRecord.getMemberId());
        //params.addValue("trackComplete", collaborateRecord.isTrackComplete());

        namedParameterJdbcTemplate.update(query, params);
    }


}
