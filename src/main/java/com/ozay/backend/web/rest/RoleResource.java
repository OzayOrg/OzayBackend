package com.ozay.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ozay.backend.model.Role;
import com.ozay.backend.model.RolePermission;
import com.ozay.backend.repository.RoleMemberRepository;
import com.ozay.backend.repository.RolePermissionRepository;
import com.ozay.backend.repository.RoleRepository;
import com.ozay.backend.service.RoleService;
import com.ozay.backend.web.rest.dto.OrganizationRoleMemberDTO;
import com.ozay.backend.web.rest.errors.ErrorDTO;
import com.ozay.backend.web.rest.form.RoleFormDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by naofumiezaki on 11/18/15.
 */
@RestController
@RequestMapping("/api/role")
public class RoleResource {

    private final Logger log = LoggerFactory.getLogger(RoleResource.class);

    @Inject
    RoleService roleService;

    @Inject
    RoleRepository roleRepository;

    @Inject
    RoleMemberRepository roleMemberRepository;

    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> createRole(@RequestBody RoleFormDTO roleFormDTO) {
        log.debug("REST request to create Role : {}", roleFormDTO);
        roleService.create(roleFormDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> updateRole(@RequestBody RoleFormDTO roleFormDTO) {
        log.debug("REST request to update Role : {}", roleFormDTO);
        roleService.update(roleFormDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(
        value = "/delete",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> deleteRoles(@RequestBody List<Role> roles) {
        log.debug("REST request to delete Roles : {}", roles);

        if(roles.size() == 0){
            ErrorDTO errorDTO = new ErrorDTO("You need to select at least one role");
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }
        String errorMessage = "";
        Set<Long> ids = new HashSet<Long>();

        for(Role role : roles) {
            List<OrganizationRoleMemberDTO> organizationRoleMemberDTOs = roleMemberRepository.findOrganizationRoleMembers(role.getId());
            if (organizationRoleMemberDTOs.size() > 0) {
                if(errorMessage.length() > 0){
                    errorMessage += ", ";
                }
                errorMessage += role.getName();
            } else {
                ids.add(role.getId());
            }

        }

        if(errorMessage.length() > 0 || ids.size() == 0){
            ErrorDTO errorDTO = new ErrorDTO("You have to unassign users/members from the following roles:" + errorMessage);
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }
        roleService.delete(ids);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
