package com.ozay.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ozay.backend.domain.User;
import com.ozay.backend.model.Building;
import com.ozay.backend.model.InvitedUser;
import com.ozay.backend.model.Organization;
import com.ozay.backend.repository.*;
import com.ozay.backend.service.UserService;
import com.ozay.backend.web.rest.dto.OrganizationUserDTO;
import com.ozay.backend.web.rest.dto.UserDTO;
import com.ozay.backend.web.rest.dto.pages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naofumiezaki on 11/1/15.
 */

@RestController
@RequestMapping("/api/page")
public class PageResource {

    private final Logger log = LoggerFactory.getLogger(PageResource.class);

    @Inject
    private OrganizationRepository organizationRepository;

    @Inject
    private UserService userService;

    @Inject
    private BuildingRepository buildingRepository;

    @Inject
    private PermissionRepository permissionRepository;

    @Inject
    private RolePermissionRepository rolePermissionRepository;

    @Inject
    private RoleRepository roleRepository;

    @RequestMapping(
        value = "/management",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<PageManagementDTO> getManagementContents(){
        PageManagementDTO pageManagementDTO = new PageManagementDTO();
        pageManagementDTO.setOrganizations(organizationRepository.findAllUserCanAccess(userService.getUserWithAuthorities()));
        log.debug("REST request to update organization : {}", pageManagementDTO);
        return new ResponseEntity<>(pageManagementDTO, HttpStatus.OK);
    }

    @RequestMapping(
        value = "/organization/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<Organization> getOrganizationEditContents(@PathVariable Long id){
        Organization organization = organizationRepository.findOne(id);
        log.debug("REST request to get organization information : {}", organization);
        return new ResponseEntity<>(organization, HttpStatus.OK);
    }

    @RequestMapping(
        value = "/organization-detail/{organizationId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<PageOrganizationDetailDTO> getOrganizationDetailContents(@PathVariable Long organizationId){
        Organization organization = organizationRepository.findOne(organizationId);
        log.debug("REST request to get organization detail : {}", organization);
        PageOrganizationDetailDTO pageOrganizationDetailDTO = new PageOrganizationDetailDTO();
        pageOrganizationDetailDTO.setBuildings(buildingRepository.findAllOrganizationBuildings(organizationId));

        List<OrganizationUserDTO> organizationUserDTOs = new ArrayList<OrganizationUserDTO>();

        for(User user : organizationRepository.findAllOrganizationActivatedUser(organizationId)){
            OrganizationUserDTO organizationUserDTO = new OrganizationUserDTO();
            organizationUserDTO.setId(user.getId());
            organizationUserDTO.setFirstName(user.getFirstName());
            organizationUserDTO.setLastName(user.getLastName());
            organizationUserDTO.setEmail(user.getEmail());
            organizationUserDTOs.add(organizationUserDTO);
        }

        for(InvitedUser invitedUser : organizationRepository.findAllOrganizationInactivatedUser(organizationId)){
            OrganizationUserDTO organizationUserDTO = new OrganizationUserDTO();
            organizationUserDTO.setId(invitedUser.getId());
            organizationUserDTO.setFirstName(invitedUser.getFirstName());
            organizationUserDTO.setLastName(invitedUser.getLastName());
            organizationUserDTO.setEmail(invitedUser.getEmail());
            organizationUserDTOs.add(organizationUserDTO);
        }

        pageOrganizationDetailDTO.setOrganizationUserDTOs(organizationUserDTOs);


        return new ResponseEntity<>(pageOrganizationDetailDTO, HttpStatus.OK);
    }

    @RequestMapping(
        value = "/building-edit/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<PageBuildingEditDTO> getBuildingEditContents(@PathVariable Long id){
        Building building = buildingRepository.findOne(id);
        log.debug("REST request to get building edit : {}", building);
        PageBuildingEditDTO pageBuildingEditDTO = new PageBuildingEditDTO();
        pageBuildingEditDTO.setBuilding(building);
        return new ResponseEntity<>(pageBuildingEditDTO, HttpStatus.OK);
    }

    @RequestMapping(
        value = "/role",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<PageRoleDTO> getRoles(@RequestParam(value = "building") Long buildingId){
        log.debug("REST request to get role list");

        PageRoleDTO pageRoleDTO = new PageRoleDTO();
        pageRoleDTO.setRoles(roleRepository.findAll(buildingId));
        return new ResponseEntity<>(pageRoleDTO, HttpStatus.OK);
    }

    @RequestMapping(
        value = "/role-new",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<PageRoleEditDTO> roleNew(){
        log.debug("REST request to get role new  ");

        PageRoleEditDTO pageRoleEditDTO = new PageRoleEditDTO();
        pageRoleEditDTO.setPermissions(permissionRepository.findRolePermissions());
        return new ResponseEntity<>(pageRoleEditDTO, HttpStatus.OK);
    }

    @RequestMapping(
        value = "/role-edit/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<PageRoleEditDTO> roleEdit(@PathVariable Long id, @RequestParam(value = "building") Long buildingId){
        log.debug("REST request to get role id : {} ", id);

        PageRoleEditDTO pageRoleEditDTO = new PageRoleEditDTO();
        pageRoleEditDTO.setRole(roleRepository.findOne(id));
        pageRoleEditDTO.setPermissions(permissionRepository.findRolePermissions());
        pageRoleEditDTO.setRoles(roleRepository.findAllExceptId(buildingId, id));
        return new ResponseEntity<>(pageRoleEditDTO, HttpStatus.OK);
    }

    @RequestMapping(
        value = "/organization-user-new",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<PageOrganizationUserDTO> organizationUserNew(){
        log.debug("REST request to create a neworganization user  ");

        PageOrganizationUserDTO pageOrganizationUserDTO = new PageOrganizationUserDTO();
        pageOrganizationUserDTO.setPermissions(permissionRepository.findOrganizationPermissions());
        return new ResponseEntity<>(pageOrganizationUserDTO, HttpStatus.OK);
    }
}
