package com.ozay.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ozay.backend.repository.OrganizationRepository;
import com.ozay.backend.repository.OrganizationUserRepository;
import com.ozay.backend.repository.UserRepository;
import com.ozay.backend.service.OrganizationUserService;
import com.ozay.backend.service.UserService;
import com.ozay.backend.web.rest.dto.OrganizationUserDTO;
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

/**
 * Created by naofumiezaki on 10/30/15.
 */

@RestController
@RequestMapping("/api/organization-user")
public class OrganizationUserResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationUserResource.class);

    @Inject
    OrganizationRepository organizationRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private OrganizationUserRepository organizationUserRepository;

    @Inject
    private OrganizationUserService organizationUserService;

    /**
     * POST  create organization
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> createOrganizationUser(@RequestBody OrganizationUserDTO organizationUserDTO) {
        return userRepository.findOneByEmail(organizationUserDTO.getEmail())
            .map(user -> {
                organizationUserDTO.setId(user.getId());
                organizationUserService.processExisitinUser(organizationUserDTO);
                return new ResponseEntity<>(HttpStatus.CREATED);
            })
            .orElseGet(() -> {
                organizationUserService.createNonExistingUser(organizationUserDTO);
                return new ResponseEntity<>(HttpStatus.CREATED);
            });
    }

    /**
     * PUT  Update organization
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> updateOrganizationUser(@RequestBody OrganizationUserDTO organizationUserDTO) {
        log.debug("REST request to update organization user : {}", organizationUserDTO);

        return userRepository.findOneByEmail(organizationUserDTO.getEmail())
            .map(user -> {
                organizationUserDTO.setId(user.getId());
                organizationUserService.processExisitinUser(organizationUserDTO);
                return new ResponseEntity<>(HttpStatus.OK);
            })
            .orElseGet(() -> {
                organizationUserService.updateNonExistingUser(organizationUserDTO);
                return new ResponseEntity<>(HttpStatus.OK);
            });
    }
}
