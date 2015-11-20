package com.ozay.backend.resultsetextractor;

import com.ozay.backend.model.InvitedUser;
import com.ozay.backend.model.OrganizationPermission;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by naofumiezaki on 11/17/15.
 */
public class InvitedUserPermissionSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<InvitedUser> invitedUsers = new ArrayList<InvitedUser>();
        InvitedUser invitedUser = null;
        while(resultSet.next()){
            if(invitedUser == null){
                invitedUser = new InvitedUser();
                invitedUser.setId(resultSet.getLong("id"));
                invitedUser.setFirstName(resultSet.getString("first_name"));
                invitedUser.setLastName(resultSet.getString("last_name"));
                invitedUser.setEmail(resultSet.getString("email"));
                invitedUser.setActivated(resultSet.getBoolean("activated"));
                invitedUser.setOrganizationPermissions(new HashSet<OrganizationPermission>());
            }
            OrganizationPermission organizationPermission = new OrganizationPermission();
            organizationPermission.setUserId(resultSet.getLong("user_id"));
            organizationPermission.setPermissionId(resultSet.getLong("permission_id "));
            organizationPermission.setOrganizationId(resultSet.getLong("organization_id "));

            if(organizationPermission.getUserId() != null || organizationPermission.getPermissionId() != null || organizationPermission.getOrganizationId() != null){
                invitedUser.getOrganizationPermissions().add(organizationPermission);
            }
        }
        return invitedUsers;
    }
}
