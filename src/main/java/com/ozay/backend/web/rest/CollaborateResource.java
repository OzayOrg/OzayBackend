package com.ozay.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ozay.backend.repository.CollaborateRepository;
import com.ozay.backend.service.CollaborateService;
import com.ozay.backend.web.rest.form.CollaborateFormDTO;
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
 * Created by RGV Krushnan on 07-04-2016.
 */
@RestController
@RequestMapping("/api/collaborate")

public class CollaborateResource {

    private final Logger log = LoggerFactory.getLogger(CollaborateResource.class);

    @Inject
    CollaborateRepository collaborateRepository;

    @Inject
    CollaborateService collaborateService;

    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> createBuilding(@RequestBody CollaborateFormDTO collaborateFormDTO) {
        log.debug("REST request to save Collaborate : {}", collaborateFormDTO);
        try{
            collaborateService.processCollaborate(collaborateFormDTO);

            return new ResponseEntity<>(collaborateFormDTO, HttpStatus.CREATED);

        } catch(Exception e){
            return new ResponseEntity<>(collaborateFormDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
