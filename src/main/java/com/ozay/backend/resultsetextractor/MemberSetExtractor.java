package com.ozay.backend.resultsetextractor;

import com.ozay.backend.model.Building;
import com.ozay.backend.model.Member;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naofumiezaki on 10/30/15.
 */
public class MemberSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Member> list = new ArrayList<Member>();

        while(resultSet.next()){
            Member member = new Member();
            member.setId(resultSet.getLong("id"));
            member.setFirstName(resultSet.getString("first_name"));
            member.setLastName(resultSet.getString("last_name"));
            member.setEmail(resultSet.getString("email"));
            member.setUserId(resultSet.getLong("user_id"));
            member.setOrganizationUserId(resultSet.getLong("organization_user_id"));
            member.setDeleted(resultSet.getBoolean("deleted"));

            list.add(member);
        }
        return list;
    }

}
