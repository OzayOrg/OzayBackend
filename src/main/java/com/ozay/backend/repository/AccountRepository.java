package com.ozay.backend.repository;

import com.ozay.backend.domain.User;
import com.ozay.backend.model.AccountInformation;
import com.ozay.backend.model.AccountPermission;
import com.ozay.backend.resultsetextractor.AccountPermissionResultSetExtractor;
import com.ozay.backend.resultsetextractor.AccountSimpleResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(AccountRepository.class);
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public AccountInformation getLoginUserInformation(User user){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", user.getId());
        String query = "SELECT s.id as s_id, s.user_id as s_user_id, ou.organization_id as organization_id from jhi_user u LEFT JOIN subscription s ON u.id = s.user_id AND (s.date_from <= now() AND s.date_to > now() ) LEFT JOIN organization_user ou ON u.id = ou.user_id WHERE u.id =:userId AND (s.user_id IS NOT NULL OR ou.user_id IS NOT NULL)";

        List<AccountInformation> accountInformationList = (List<AccountInformation>)namedParameterJdbcTemplate.query(query, params, new AccountSimpleResultSetExtractor());
        AccountInformation accountInformation = null;
        if(accountInformationList.size() > 0){
            accountInformation = accountInformationList.get(0);
        }
        System.out.println("simple");
        System.out.println(accountInformation);
        return accountInformation;
    }

    public AccountInformation getLoginUserInformation(User user, Long buildingId, Long organizationId){

        String query = "SELECT s.id as subscription_id, p.key " +
            "FROM jhi_user u " +
            "LEFT JOIN member m ON u.id =  m.user_id AND m.building_id = :buildingId " +
            "LEFT JOIN role_member rm ON rm.member_id = m.id " +
            "LEFT JOIN role_permission rp ON rp.role_id = rm.role_id " +
            "LEFT JOIN permission p ON p.id = rp.permission_id " +
            "LEFT JOIN subscription s ON s.user_id = u.id AND s.deleted = false " +
            "LEFT JOIN organization o ON o.user_id = u.id " +
            "LEFT JOIN building b ON b.organization_id = o.id AND b.id = :buildingId " +
            "WHERE u.id = :userId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", user.getId());
        params.addValue("buildingId", buildingId);

        List<AccountPermission> list1 = (List<AccountPermission>)namedParameterJdbcTemplate.query(query, params, new AccountPermissionResultSetExtractor());
        log.debug("list1 {}", list1);


        String query2 = "SELECT s.id as subscription_id , p.key " +
            "FROM jhi_user u " +
            "LEFT JOIN subscription s ON s.user_id = u.id AND s.deleted = false " +
            "LEFT JOIN organization o ON o.user_id = u.id  or o.id = :organizationId " +
            "LEFT JOIN organization_user ou ON o.id = ou.organization_id AND ou.user_id = u.id " +
            "LEFT JOIN organization_user_permission oup ON ou.id = oup.organization_user_id " +
            "LEFT JOIN permission p ON p.id = oup.permission_id " +
            "WHERE u.id = :userId";

        params = new MapSqlParameterSource();
        params.addValue("userId", user.getId());
        params.addValue("organizationId", organizationId);

        List<AccountPermission> list2 = (List<AccountPermission>)namedParameterJdbcTemplate.query(query2, params, new AccountPermissionResultSetExtractor());
        log.debug("list2 {}", list2);

        AccountInformation accountInformation = new AccountInformation();
        accountInformation.setAuthorities(new ArrayList<String>());

        for(AccountPermission accountPermission : list1){
            if(accountPermission.getSubscriberId() != null && accountPermission.getSubscriberId() > 0){
                accountInformation.setSubscriberId(accountPermission.getSubscriberId());
                accountInformation.getAuthorities().add("ROLE_SUBSCRIBER");
            }
            if(accountPermission.getKey() != null){
                accountInformation.getAuthorities().add(accountPermission.getKey());
            }
        }
        boolean organizationHasAccess = false;

        for(AccountPermission accountPermission : list2){
            if(accountPermission.getSubscriberId() != null && accountPermission.getSubscriberId() > 0){
                accountInformation.setOrganizationSubscriberId(accountPermission.getSubscriberId());
                accountInformation.getAuthorities().add("ROLE_ORGANIZATION_SUBSCRIBER");
            }
            if(accountPermission.getKey() != null){
                accountInformation.getAuthorities().add(accountPermission.getKey());
                if(organizationHasAccess == false){
                    accountInformation.getAuthorities().add("ORGANIZATION_HAS_ACCESS");
                }
            }
        }
        System.out.println("complicated");
        System.out.println(accountInformation);
        return accountInformation;
    }
}
