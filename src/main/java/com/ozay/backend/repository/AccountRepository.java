package com.ozay.backend.repository;

import com.ozay.backend.domain.User;
import com.ozay.backend.model.AccountInformation;
import com.ozay.backend.resultsetextractor.AccountResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naofumiezaki on 11/1/15.
 */
@Repository
public class AccountRepository {
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<AccountInformation> getAllByBuildingId(long buildingId){
        String query = "SELECT * FROM account where building_id = :building_id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        return null;
    }

    public AccountInformation getLoginUserInformation(User user){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", user.getId());
        String query = "SELECT count(u.id) from jhi_user u LEFT JOIN subscription s ON u.id = s.user_id LEFT JOIN organization_user ou ON u.id = ou.user_id WHERE u.id =:userId AND (s.user_id IS NOT NULL OR ou.user_id IS NOT NULL)";

        Long count = namedParameterJdbcTemplate.queryForObject(query, params, Long.class);
        AccountInformation accountInformation = new AccountInformation();
        if(count > 0){
            List<String> authorityList = new ArrayList<String>();
            authorityList.add("ORGANIZATION_HAS_ACCESS");
            accountInformation.setAuthorities(authorityList);
        }
        return accountInformation;
    }

    public AccountInformation getLoginUserInformation(User user, Long buildingId, Long organizationId){

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", user.getId());

        String query = "SELECT DISTINCT s.id as s_id, s.user_id as s_user_id, o.id as organization_id, rp.name as rp_name, op.name as op_name " +
            "FROM jhi_user u " +
            "LEFT JOIN subscription s ON s.user_id = u.id " +
            "LEFT JOIN organization o ON o.user_id = s.user_id  " +
            "LEFT JOIN member m ON  u.id =  m.user_id " +
            "LEFT JOIN role_member rm ON rm.member_id = m.id " +
            "LEFT JOIN role_permission rp ON rp.role_id = rm.role_id " +
            "LEFT JOIN organization_permission op ON op.user_id = u.id AND organization_id = ";

        if(organizationId != null){
            query += " :organizationId ";
            params.addValue("organizationId", organizationId);

            query +=
                "WHERE u.id = :id AND ((rp.role_id is not null) OR op.name is not null OR o.id is not null)";
        }
        else if(buildingId != null){
            query += " (SELECT organization_id from building where id = :buildingId) ";
            params.addValue("buildingId", buildingId);
            query +=
                "WHERE u.id = :id AND ((rp.role_id is not null AND m.building_id = :buildingId) OR op.name is not null OR o.id is not null)";
        }



        List<AccountInformation> accountInformationList = (List<AccountInformation>)namedParameterJdbcTemplate.query(query, params, new AccountResultSetExtractor());
        AccountInformation accountInformation = null;
        if(accountInformationList.size() > 0){
            accountInformation = accountInformationList.get(0);
        }


////        HashMap<String,Authority> map = new HashMap<String,Authority>();
//        System.out.println(account);


        return accountInformation;
    }
}
