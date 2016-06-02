package com.ozay.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ozay.backend.domain.User;
import com.ozay.backend.model.Collaborate;
import com.ozay.backend.repository.CollaborateRepository;
import com.ozay.backend.service.UserService;
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
@RequestMapping("/api/collaborate")
public class CollaborateResource {

    private final Logger log = LoggerFactory.getLogger(CollaborateResource.class);

    @Inject
    CollaborateRepository collaborateRepository;

    @Inject
    UserService userService;


    /**
     * POST  create organization
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> createCollaborate(@RequestBody Collaborate collaborate) {
        log.debug("REST request to save collraborate : {}", collaborate);
        User user = userService.getUserWithAuthorities();
        collaborate.setCreatedBy(user.getId());
        collaborateRepository.create(collaborate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * PUT  Update organization
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> updateOrganization(@RequestBody Collaborate collaborate) {
        log.debug("REST request to update collaborate : {}", collaborate);

        collaborateRepository.update(collaborate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
