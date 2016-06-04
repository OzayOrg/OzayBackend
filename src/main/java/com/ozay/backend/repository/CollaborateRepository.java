package com.ozay.backend.repository;

import com.ozay.backend.config.Constants;
import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.CollaborateDate;
import com.ozay.backend.resultsetextractor.CollaborateResultSetExtractor;
import com.ozay.backend.security.SecurityUtils;
import com.ozay.backend.utility.DateTimeUtility;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by naofumiezaki on 5/24/16.
 */
@Repository
public class CollaborateRepository {

    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private PermissionRepository permissionRepository;



    public Collaborate findOneById(Long id){
        String query = "SELECT c.*, cd.id as cd_id, cd.collaborate_id as cd_collaborate_id, cd.issue_date, cm.modified_date as cm_modified_date,  m.id as m_id, m.first_name, m.last_name, cm.selected, cm.collaborate_date_id FROM collaborate c INNER JOIN collaborate_date cd ON cd.collaborate_id = c.id INNER JOIN collaborate_member cm ON cm.collaborate_date_id = cd.id INNER JOIN member m ON m.deleted = false AND m.id = cm.member_id WHERE c.id = :id ORDER BY c.id, issue_date";
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", id);
        List<Collaborate> list = (List<Collaborate>)namedParameterJdbcTemplate.query(query, params, new CollaborateResultSetExtractor());
        if(list.size() < 1){
            return null;
        } else {
            return list.get(0);
        }
    }
    public List<Collaborate> findAllByBuildingForTrack(Long buildingId, Long page){
        return this.findAllByBuilding(buildingId, true, page);
    }

    public long getTotalNumberOfAccessibleCollaborates(Long buildingId, boolean track){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("buildingId", buildingId);
        String extraQuery = "";

        if(SecurityUtils.isUserInRole("ROLE_ADMIN") == false && accountRepository.isSubscriber(buildingId) == false &&  permissionRepository.validateMemberInterceptor(buildingId, "COLLABORATE_GET") == false){
            params.addValue("login", SecurityUtils.getCurrentLogin());
            extraQuery = " INNER JOIN jhi_user u ON u.id = m.user_id AND u.login = :login ";
        }
        String query = null;
        if(track == true){
            query = "SELECT COUNT(id) from ((SELECT c.id, max(issue_date) as issue_date from collaborate_date cd INNER join collaborate c on cd.collaborate_id = c.id AND c.building_id = :buildingId INNER JOIN collaborate_member cm ON cd.id = cm.collaborate_date_id INNER JOIN member m ON cm.member_id = m.id " + extraQuery + " group by c.id)) t1 where issue_date >= now()";
        } else {
            query = "SELECT COUNT(id) from ((SELECT c.id, max(issue_date) as issue_date from collaborate_date cd INNER join collaborate c on cd.collaborate_id = c.id AND c.building_id = :buildingId INNER JOIN collaborate_member cm ON cd.id = cm.collaborate_date_id INNER JOIN member m ON cm.member_id = m.id " + extraQuery + " group by c.id)) t1 where issue_date < now()";
        }
        return namedParameterJdbcTemplate.queryForObject(query, params, Long.class);
    }

    public Set<Integer> getAccessibleCollaborateIds(Long buildingId, Long page, boolean track){
        long limit = Constants.APP_QUERY_LIMIT;
        if(page == null){
            page = (long)1;
        }
        long offset = (page - 1) * limit;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("limit", limit);
        params.addValue("offset", offset);
        params.addValue("buildingId", buildingId);
        String extraQuery = "";

        if(SecurityUtils.isUserInRole("ROLE_ADMIN") == false && accountRepository.isSubscriber(buildingId) == false &&  permissionRepository.validateMemberInterceptor(buildingId, "COLLABORATE_GET") == false){
            params.addValue("login", SecurityUtils.getCurrentLogin());
            extraQuery = " INNER JOIN jhi_user u ON u.id = m.user_id AND u.login = :login ";
        }
        String query = null;
        if(track == true){
            query = "SELECT id from ((SELECT c.id, max(issue_date) as issue_date, status from collaborate_date cd INNER join collaborate c on cd.collaborate_id = c.id AND c.building_id = :buildingId INNER JOIN collaborate_member cm ON cd.id = cm.collaborate_date_id INNER JOIN member m ON cm.member_id = m.id " + extraQuery + " group by c.id)) t1 where issue_date >= now() AND status in (0, 1) ORDER BY issue_date DESC limit :limit offset :offset";
        } else {
            query = "SELECT id from ((SELECT c.id, max(issue_date) as issue_date, status from collaborate_date cd INNER join collaborate c on cd.collaborate_id = c.id AND c.building_id = :buildingId INNER JOIN collaborate_member cm ON cd.id = cm.collaborate_date_id INNER JOIN member m ON cm.member_id = m.id " + extraQuery + " group by c.id)) t1 where issue_date < now() OR status in (2) ORDER BY issue_date DESC limit :limit offset :offset";
        }
        List<Integer> list = namedParameterJdbcTemplate.queryForList(query, params, Integer.class);
        Set<Integer> ids = new HashSet<Integer>(list);

        return ids;
    }

    public List<Collaborate> findAllByBuilding(Long buildingId, boolean track, Long page){
        Set<Integer> ids = this.getAccessibleCollaborateIds(buildingId,page,track);

        if(ids.size() == 0){
            return new ArrayList<Collaborate>();
        }
        String query = "SELECT c.*, cd.id as cd_id, cd.collaborate_id as cd_collaborate_id, cd.issue_date, m.id as m_id, cm.modified_date as cm_modified_date, m.first_name, m.last_name, cm.selected, cm.collaborate_date_id FROM collaborate c INNER JOIN collaborate_member cm ON c.id = cm.collaborate_id INNER JOIN member m ON m.deleted = false and m.id = cm.member_id LEFT JOIN collaborate_date cd ON cd.collaborate_id = c.id WHERE c.id IN (:ids) ORDER BY issue_date DESC ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ids", ids);

        return (List<Collaborate>)namedParameterJdbcTemplate.query(query, params, new CollaborateResultSetExtractor());
    }

    public List<Collaborate> findAllByBuildingForArchive(Long buildingId, Long page){
        return this.findAllByBuilding(buildingId, false, page);
    }

    public void create(Collaborate collaborate){
        String query = "INSERT INTO collaborate (building_id, subject, message, response, created_by, created_date) VALUES (:buildingId, :subject, :message, :response, :createdBy, now()) RETURNING id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("buildingId", collaborate.getBuildingId());
        params.addValue("subject", collaborate.getSubject());
        params.addValue("message", collaborate.getMessage());
        params.addValue("response", collaborate.getResponse());
        params.addValue("createdBy", collaborate.getCreatedBy());
        Long id = namedParameterJdbcTemplate.queryForObject(query, params, Long.class );
        collaborate.setId(id);
    }

    public void update(Collaborate collaborate){
        String query = "UPDATE collaborate SET status = :status, collaborate_date_id=:collaborateDateId WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", collaborate.getId());
        params.addValue("status", collaborate.getStatus());
        params.addValue("collaborateDateId", collaborate.getCollaborateDateId());
        namedParameterJdbcTemplate.update(query, params);
    }

    // Deleted = 1 soft delete
    public void delete(Collaborate collaborate){
        String query = "UPDATE collaborate SET deleted=true, last_modified_by=:lastModifiedBy  last_modified_date=NOW()) WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", collaborate.getId());
        params.addValue("lastModifiedBy", collaborate.getModifiedBy());
        namedParameterJdbcTemplate.update(query, params );
    }
}
