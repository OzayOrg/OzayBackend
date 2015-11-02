package com.ozay.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ozay.backend.repository.OrganizationRepository;
import com.ozay.backend.service.UserService;
import com.ozay.backend.web.rest.dto.pages.PageManagementDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.jvm.hotspot.debugger.Page;

import javax.inject.Inject;

/**
 * Created by naofumiezaki on 11/1/15.
 */

@RestController
@RequestMapping("/api/pages")
public class PageResource {
    @Inject
    private OrganizationRepository organizationRepository;

    @Inject
    private UserService userService;

    @RequestMapping(
        value = "/management",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<PageManagementDTO> getManagementContents(){
        PageManagementDTO pageManagementDTO = new PageManagementDTO();
        pageManagementDTO.setOrganizations(organizationRepository.findAllUserCanAccess(userService.getUserWithAuthorities()));
        return new ResponseEntity<>(pageManagementDTO, HttpStatus.OK);
    }
}
