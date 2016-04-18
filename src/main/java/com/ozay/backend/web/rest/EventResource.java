package com.ozay.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ozay.backend.domain.User;
import com.ozay.backend.model.Event;
import com.ozay.backend.repository.EventRepository;
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
@RequestMapping("/api/events")
public class EventResource {

    private final Logger log = LoggerFactory.getLogger(EventResource.class);

    @Inject
    EventRepository eventRepository;

    @Inject
    UserService userService;


    /**
     * POST  create organization
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        log.debug("REST request to save event : {}", event);
        User user = userService.getUserWithAuthorities();
        event.setId(user.getId());
        eventRepository.create(event);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * PUT  Update organization
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> updateEvent(@RequestBody Event event) {
        log.debug("REST request to update event : {}", event);

        eventRepository.update(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
