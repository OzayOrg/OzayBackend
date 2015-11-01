package com.ozay.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ozay.backend.domain.Authority;
import com.ozay.backend.domain.User;
import com.ozay.backend.model.Building;
import com.ozay.backend.repository.BuildingRepository;
import com.ozay.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naofumiezaki on 10/30/15.
 */

@RestController
@RequestMapping("/api/buildings")
public class BuildingResource {

    private final Logger log = LoggerFactory.getLogger(BuildingResource.class);

    @Inject
    BuildingRepository buildingRepository;

    @Inject
    UserService userService;


    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<Building>> getAll(){

        User user = userService.getUserWithAuthorities();

        boolean isAdmin = false;
        for(Authority authority : user.getAuthorities()){
            if(authority.getName().equals("ROLE_ADMIN")){
                isAdmin = true;
                break;
            }
        }
        List<Building> buildingList = new ArrayList<Building>();

        if(isAdmin){
            buildingList = buildingRepository.findAll();
        } else {
            buildingList = buildingRepository.findAllUserCanAccess(user);
        }

        return new ResponseEntity<List<Building>>(buildingList, HttpStatus.OK);
    }
}
