package com.ozay.backend.resultsetextractor;

import com.ozay.backend.model.InvitedUser;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naofumiezaki on 11/17/15.
 */
public class InvitedUserSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<InvitedUser> invitedUsers = new ArrayList<InvitedUser>();
        while(resultSet.next()){
            InvitedUser invitedUser = new InvitedUser();
            invitedUser.setId(resultSet.getLong("id"));
            invitedUser.setFirstName(resultSet.getString("first_name"));
            invitedUser.setLastName(resultSet.getString("last_name"));
            invitedUser.setEmail(resultSet.getString("email"));
            invitedUser.setActivated(resultSet.getBoolean("activated"));
            invitedUser.setActivationKey(resultSet.getString("activation_key"));
            invitedUsers.add(invitedUser);
        }
        return invitedUsers;
    }
}
