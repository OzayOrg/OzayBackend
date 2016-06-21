package com.ozay.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ozay.backend.domain.User;
import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.CollaborateField;
import com.ozay.backend.model.CollaborateMember;
import com.ozay.backend.model.Member;
import com.ozay.backend.repository.CollaborateRepository;
import com.ozay.backend.repository.MemberRepository;
import com.ozay.backend.repository.UserRepository;
import com.ozay.backend.security.SecurityUtils;
import com.ozay.backend.service.CollaborateService;
import com.ozay.backend.service.UserService;
import com.ozay.backend.web.rest.errors.ErrorDTO;
import com.ozay.backend.web.rest.form.CollaborateCreateFormDTO;
import com.ozay.backend.web.rest.form.CollaborateDetailFormDTO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    CollaborateService collaborateService;

    @Inject
    UserService userService;

    @Inject
    MemberRepository memberRepository;

    @Inject
    UserRepository userRepository;


    /**
     * POST  create collaborate
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> createCollaborate(@RequestBody CollaborateCreateFormDTO collaborateCreateFormDTO) {
        log.debug("REST request to save collraborate : {}", collaborateCreateFormDTO);

        int size = collaborateCreateFormDTO.getCollaborateFields().size();
        if(size == 0){
            return new ResponseEntity<>(new ErrorDTO("Sorry, Please add at least one Calendar Date"), HttpStatus.BAD_REQUEST);
        }
        if(collaborateCreateFormDTO.getCollaborate().getResponse() == Collaborate.RSVP){
            if(size > 1) {
                return new ResponseEntity<>(new ErrorDTO("Sorry, Need size 1"), HttpStatus.BAD_REQUEST);
            }
        }
        collaborateService.create(collaborateCreateFormDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * PUT  Complete Collaborate
     */
    @RequestMapping(
        value = "/complete",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> completeCollaborate(@RequestParam(value = "building") Long buildingId, @RequestBody Collaborate collaborate) {
        Collaborate currentCollaborate = collaborateRepository.findOneById(collaborate.getId());
        if(currentCollaborate == null){
            return new ResponseEntity<>(new ErrorDTO("Invalid Request"), HttpStatus.BAD_REQUEST);
        }
        if(currentCollaborate.getStatus() == Collaborate.STATUS_CANCELED){
            return new ResponseEntity<>(new ErrorDTO("Invalid Request"), HttpStatus.BAD_REQUEST);
        }
        else if(currentCollaborate.getStatus() == Collaborate.STATUS_COMPLETED){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        currentCollaborate.setStatus(Collaborate.STATUS_COMPLETED);
        if(currentCollaborate.getResponse() == Collaborate.RSVP){
            Long id = currentCollaborate.getCollaborateFields().get(0).getId();
            currentCollaborate.setCollaborateFieldId(id);
        } else if(currentCollaborate.getResponse() == Collaborate.CALENDER){
            currentCollaborate.setCollaborateFieldId(collaborate.getCollaborateFieldId());
        }
        collaborateService.complete(currentCollaborate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * PUT  Cancel Collaborate
     */
    @RequestMapping(
        value = "/cancel",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> cancelCollaborate(@RequestParam(value = "building") Long buildingId, @RequestBody Collaborate collaborate) {
        Collaborate currentCollaborate = collaborateRepository.findOneById(collaborate.getId());
        if(currentCollaborate == null){
            return new ResponseEntity<>(new ErrorDTO("Invalid Request"), HttpStatus.BAD_REQUEST);
        }
        currentCollaborate.setCollaborateFieldId(null);
        currentCollaborate.setStatus(Collaborate.STATUS_CANCELED);
        collaborateRepository.update(currentCollaborate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * PUT  update Collaborate
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> updateCollaborate(@RequestParam(value = "building") Long buildingId, @RequestBody CollaborateDetailFormDTO collaborateDetailFormDTO) {
        log.debug("REST request to update collraborate : {}", collaborateDetailFormDTO);

        if(SecurityUtils.isUserInRole("ROLE_ADMIN")){
            return new ResponseEntity<>(new ErrorDTO("ADMIN cannot make this request"), HttpStatus.BAD_REQUEST);
        }

        if(collaborateDetailFormDTO.getCollaborateId() == null){
            return new ResponseEntity<>(new ErrorDTO("Invalid Request"), HttpStatus.BAD_REQUEST);
        }
        Collaborate currentCollaborate = collaborateRepository.findOneById(collaborateDetailFormDTO.getCollaborateId());

        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin()).get();

        Member member = memberRepository.findOneByUserIdAndBuildingId(user.getId(), buildingId);

        if(member == null){
            return new ResponseEntity<>(new ErrorDTO("You cannot make this request"), HttpStatus.BAD_REQUEST);
        }

        boolean isBigger = false;
        boolean hasMember = false;

        for(CollaborateField cd : currentCollaborate.getCollaborateFields()){
            if(cd.getIssueDate().isAfter(new DateTime().getMillis()) ){
                isBigger = true;
            }
            for(CollaborateMember cm : cd.getCollaborateMembers()){
                if(member.getId() == cm.getMember().getId()){
                    hasMember = true;
                    break;
                }
            }

            if(hasMember == true && isBigger == true){
                break;
            }
        }
        if(isBigger == false){
            return new ResponseEntity<>(new ErrorDTO("This is already expired"), HttpStatus.BAD_REQUEST);
        }
        if(hasMember == false){
            return new ResponseEntity<>(new ErrorDTO("You don't have acces"), HttpStatus.BAD_REQUEST);
        }
        collaborateDetailFormDTO.setMemberId(member.getId());

        collaborateService.update(collaborateDetailFormDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete  Update organization
     */
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> updateOrganization(@PathVariable Long id) {
        log.debug("REST request to delete collaborate : {}", id);
        User user = userService.getUserWithAuthorities();
        Collaborate collaborate = collaborateRepository.findOneById(id);
        if(collaborate == null){
            log.debug("Collaborate not found", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else if(collaborate.getCreatedBy() != user.getId() && !SecurityUtils.isUserInRole("ROLE_ADMIN") ){
            log.debug("Collaborate Delete: Bad request by non-created User", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        collaborateRepository.delete(collaborate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
