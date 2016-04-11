package com.ozay.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ozay.backend.model.CollaborateRecord;
import com.ozay.backend.repository.CollaborateRecordRepository;
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
@RequestMapping("/api/collaborate-record")
public class CollaborateRecordResource {

    private final Logger log = LoggerFactory.getLogger(CollaborateRecordResource.class);

    @Inject
    CollaborateRecordRepository collaborateRecordRepository;

    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> updateCollaborateRecord(@RequestBody CollaborateRecord collaborateRecord) {
        log.debug("REST request to update CollaborateRecord : {}", collaborateRecord);
        collaborateRecordRepository.update(collaborateRecord);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
