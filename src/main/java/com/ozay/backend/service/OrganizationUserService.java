package com.ozay.backend.service;

import com.ozay.backend.domain.User;
import com.ozay.backend.model.InvitedUser;
import com.ozay.backend.model.OrganizationUser;
import com.ozay.backend.model.OrganizationUserPermission;
import com.ozay.backend.repository.InvitedUserRepository;
import com.ozay.backend.repository.OrganizationUserPermissionRepository;
import com.ozay.backend.repository.OrganizationUserRepository;
import com.ozay.backend.repository.UserRepository;
import com.ozay.backend.web.rest.dto.OrganizationUserDTO;
import com.ozay.backend.web.rest.form.OrganizationUserRegisterDTO;
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
    OrganizationUserRepository organizationUserRepository;

    @Inject
    OrganizationUserPermissionRepository organizationUserPermissionRepository;

    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    // If A user record exists in jhi_user table = Existing user
    public void processExistingUser(OrganizationUserDTO organizationUserDTO){
        processPermissions(organizationUserDTO);
    }



    public void createNonExistingUser(OrganizationUserDTO organizationUserDTO){
        // Create Invited User Record
        InvitedUser invitedUser = new InvitedUser();
        invitedUser.setFirstName(organizationUserDTO.getFirstName());
        invitedUser.setLastName(organizationUserDTO.getLastName());
        invitedUser.setEmail(organizationUserDTO.getEmail());
        invitedUserRepository.create(invitedUser);

        // Create a record for bridge
        organizationUserDTO.setUserId(invitedUser.getId());
        OrganizationUser organizationUser = new OrganizationUser();
        organizationUser.setUserId(invitedUser.getId());
        organizationUser.setOrganizationId(organizationUserDTO.getOrganizationId());
        organizationUser.setActivated(false);

        organizationUserRepository.create(organizationUser);

        // Set Organization User ID
        organizationUserDTO.setId(organizationUser.getId());

        // Add permission records
        processPermissions(organizationUserDTO);

    }

    public void updateNonExistingUser(OrganizationUserDTO organizationUserDTO){
        InvitedUser invitedUser = new InvitedUser();
        invitedUser.setId(organizationUserDTO.getUserId());
        invitedUser.setFirstName(organizationUserDTO.getFirstName());
        invitedUser.setLastName(organizationUserDTO.getLastName());
        invitedUser.setEmail(organizationUserDTO.getEmail());
        invitedUserRepository.update(invitedUser);
        processPermissions(organizationUserDTO);
    }

    public void processPermissions(OrganizationUserDTO organizationUserDTO){
        organizationUserPermissionRepository.deleteAllByOrganizationUserId(organizationUserDTO.getId());
        for(OrganizationUserPermission organizationUserPermission : organizationUserDTO.getOrganizationUserPermissions()){
            organizationUserPermission.setOrganizationUserId(organizationUserDTO.getId());
            organizationUserPermissionRepository.create(organizationUserPermission);
        }
    }

    public void createUserInformation(OrganizationUserRegisterDTO organizationUserRegisterDTO, InvitedUser invitedUser, OrganizationUser organizationUser){
        User user = userService.createUserInformation(organizationUserRegisterDTO.getLogin(), organizationUserRegisterDTO.getPassword(),
            invitedUser.getFirstName(), invitedUser.getLastName(), invitedUser.getEmail().toLowerCase(),
            organizationUserRegisterDTO.getLangKey());
        user.setActivated(true);
        user.setActivationKey(invitedUser.getActivationKey());
        userRepository.save(user);

        // Update organization user table
        organizationUser.setUserId(user.getId());
        organizationUser.setActivated(true);
        organizationUserRepository.update(organizationUser);
    }
}
