package com.ozay.backend.resultsetextractor;

import com.ozay.backend.model.Organization;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naofumiezaki on 11/1/15.
 */
public class OrganizationSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Organization> organizations = new ArrayList<Organization>();

        while(resultSet.next()){
            Organization organization = new Organization();
            organization.setId(resultSet.getLong("id"));
            organization.setUserId(resultSet.getLong("user_id"));
            organization.setName(resultSet.getString("name"));
            organization.setAddress1(resultSet.getString("address_1"));
            organization.setAddress2(resultSet.getString("address_2"));
            organization.setPhone(resultSet.getString("phone"));
            organization.setCountry(resultSet.getString("country"));
            organization.setState(resultSet.getString("state"));
        }

        return organizations;
    }
}
