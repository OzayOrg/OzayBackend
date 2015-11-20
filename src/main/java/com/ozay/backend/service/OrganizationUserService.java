package com.ozay.backend.service;

import com.ozay.backend.model.InvitedUser;
import com.ozay.backend.model.OrganizationPermission;
import com.ozay.backend.repository.InvitedUserRepository;
import com.ozay.backend.repository.OrganizationInvitedUserPermissionRepository;
import com.ozay.backend.repository.OrganizationInvitedUserRepository;
import com.ozay.backend.repository.OrganizationUserRepository;
import com.ozay.backend.web.rest.dto.OrganizationUserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by naofumiezaki on 11/19/15.
 */
@Service
@Transactional
public class OrganizationUserService {

    @Inject
    InvitedUserRepository invitedUserRepository;

    @Inject
    OrganizationInvitedUserPermissionRepository organizationInvitedUserPermissionRepository;

    @Inject
    OrganizationUserRepository organizationUserRepository;

    @Inject
    OrganizationInvitedUserRepository organizationInvitedUserRepository;

    public void processExisitinUser(OrganizationUserDTO organizationUserDTO){
        processUserPermission(organizationUserDTO);
    }


    public void createNonExistingUser(OrganizationUserDTO organizationUserDTO){
        InvitedUser invitedUser = new InvitedUser();
        invitedUser.setFirstName(organizationUserDTO.getFirstName());
        invitedUser.setLastName(organizationUserDTO.getLastName());
        invitedUser.setEmail(organizationUserDTO.getEmail());
        invitedUser.setOrganizationId(organizationUserDTO.getOrganizationId());
        invitedUserRepository.create(invitedUser);
        organizationInvitedUserRepository.create(invitedUser);
        organizationUserDTO.setId(invitedUser.getId());
        processInvitedUserPermission(organizationUserDTO);
    }

    public void updateNonExistingUser(OrganizationUserDTO organizationUserDTO){
        InvitedUser invitedUser = new InvitedUser();
        invitedUser.setId(organizationUserDTO.getId());
        invitedUser.setFirstName(organizationUserDTO.getFirstName());
        invitedUser.setLastName(organizationUserDTO.getLastName());
        invitedUser.setEmail(organizationUserDTO.getEmail());
        invitedUserRepository.update(invitedUser);
        processInvitedUserPermission(organizationUserDTO);
    }

    private void processUserPermission(OrganizationUserDTO organizationUserDTO){
        organizationUserRepository.deleteAllByUser(organizationUserDTO);
        for(OrganizationPermission organizationPermission : organizationUserDTO.getOrganizationPermissions()){
            organizationPermission.setPermissionId(organizationUserDTO.getId());
            organizationUserRepository.create(organizationPermission);
        }
    }

    private void processInvitedUserPermission(OrganizationUserDTO organizationUserDTO){
        organizationInvitedUserPermissionRepository.deleteAllByUser(organizationUserDTO);
        for(OrganizationPermission organizationPermission : organizationUserDTO.getOrganizationPermissions()){
            organizationPermission.setUserId(organizationUserDTO.getId());
            organizationPermission.setOrganizationId(organizationUserDTO.getOrganizationId());
            organizationInvitedUserPermissionRepository.create(organizationPermission);
        }
    }
}
