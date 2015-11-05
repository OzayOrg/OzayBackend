package com.ozay.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ozay.backend.model.Organization;
import com.ozay.backend.repository.BuildingRepository;
import com.ozay.backend.repository.OrganizationRepository;
import com.ozay.backend.service.UserService;
import com.ozay.backend.web.rest.dto.pages.PageManagementDTO;
import com.ozay.backend.web.rest.dto.pages.PageOrganizationDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.jvm.hotspot.debugger.Page;

import javax.inject.Inject;

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
        value = "/organization-detail/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<PageOrganizationDetailDTO> getOrganizationDetailContents(@PathVariable Long id){
        Organization organization = organizationRepository.findOne(id);
        log.debug("REST request to get organization detail : {}", organization);
        PageOrganizationDetailDTO pageOrganizationDetailDTO = new PageOrganizationDetailDTO();
        pageOrganizationDetailDTO.setBuildings(buildingRepository.findAllOrganizationBuildings(id));
        return new ResponseEntity<>(pageOrganizationDetailDTO, HttpStatus.OK);
    }
}
