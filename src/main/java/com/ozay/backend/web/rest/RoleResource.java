package com.ozay.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ozay.backend.model.Building;
import com.ozay.backend.repository.RoleRepository;
import com.ozay.backend.service.RoleService;
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

/**
 * Created by naofumiezaki on 11/18/15.
 */
@RestController
@RequestMapping("/api/role")
public class RoleResource {

    private final Logger log = LoggerFactory.getLogger(RoleResource.class);

    @Inject
    RoleService roleService;

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
}
