package com.ozay.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ozay.backend.domain.User;
import com.ozay.backend.model.InvitedUser;
import com.ozay.backend.model.OrganizationUser;
import com.ozay.backend.repository.InvitedUserRepository;
import com.ozay.backend.repository.OrganizationRepository;
import com.ozay.backend.repository.OrganizationUserRepository;
import com.ozay.backend.repository.UserRepository;
import com.ozay.backend.service.MailService;
import com.ozay.backend.service.OrganizationUserService;
import com.ozay.backend.service.UserService;
import com.ozay.backend.web.rest.dto.OrganizationUserDTO;
import com.ozay.backend.web.rest.errors.ErrorDTO;
import com.ozay.backend.web.rest.form.OrganizationUserRegisterDTO;
import com.ozay.backend.web.rest.util.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @Inject
    InvitedUserRepository invitedUserRepository;

    @Inject
    private MailService mailService;

    /**
     * POST  create organization user
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> createOrganizationUser(@RequestBody OrganizationUserDTO organizationUserDTO, HttpServletRequest request) {
        return userRepository.findOneByEmail(organizationUserDTO.getEmail())
            .map(user -> {
                organizationUserDTO.setUserId(user.getId());
                organizationUserService.processExistingUser(organizationUserDTO);
                String baseUrl = UrlUtil.baseUrlGenerator(request);
                mailService.sendNewOrganizationUserWelcomeEmail(organizationUserDTO, baseUrl);
                return new ResponseEntity<>(HttpStatus.CREATED);
            })
            .orElseGet(() -> {
                List<InvitedUser> invitedUsers = invitedUserRepository.findAllByOrganizationIdAndEmail(organizationUserDTO.getOrganizationId(), organizationUserDTO.getEmail());
                if(invitedUsers.size() == 0){
                    organizationUserService.createNonExistingUser(organizationUserDTO);
                } else {
                    return new ResponseEntity<>(new ErrorDTO("email already in use"), HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(organizationUserDTO, HttpStatus.CREATED);
            });
    }

    /**
     * PUT  Update organization user
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> updateOrganizationUser(@Valid @RequestBody OrganizationUserDTO organizationUserDTO) {
        log.debug("REST request to update organization user : {}", organizationUserDTO);

        return Optional.ofNullable(organizationUserRepository.findOne(organizationUserDTO.getId()))
            .map(organizationUser -> {
                if(organizationUser.isActivated()){
                    organizationUserService.processPermissions(organizationUserDTO);
                } else {
                    List<InvitedUser> invitedUsers = invitedUserRepository.findAllByOrganizationIdAndEmail(organizationUserDTO.getOrganizationId(), organizationUserDTO.getEmail());
                    if(invitedUsers.size() == 0){
                        organizationUserService.updateNonExistingUser(organizationUserDTO);
                    } else {
                        if(invitedUsers.get(0).getId() != organizationUserDTO.getUserId()){
                            return new ResponseEntity<>(new ErrorDTO("email already in use"), HttpStatus.BAD_REQUEST);
                        }else{
                            organizationUserService.updateNonExistingUser(organizationUserDTO);
                        }
                    }
                }
                return new ResponseEntity<>(organizationUserDTO, HttpStatus.OK);
            })
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
    /**
     * POST  create Invitation Email
     */
    @RequestMapping(
        value = "/invite",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> sendInvitationEmail(@Valid @RequestBody OrganizationUserDTO organizationUserDTO, HttpServletRequest request) {
        String activateKey = "";
        if(organizationUserDTO.isActivated() == false){
            InvitedUser invitedUser = invitedUserRepository.findOne(organizationUserDTO.getUserId());
            activateKey = invitedUser.getActivationKey();
            String baseUrl = UrlUtil.baseUrlGenerator(request);
            mailService.sendOrganizationUserInvitationMail(organizationUserDTO, baseUrl, activateKey);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * POST  create Invitation Email
     */
    @RequestMapping(
        value = "/register",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> organizationUserRegister(@Valid @RequestBody OrganizationUserRegisterDTO organizationUserRegisterDTO, @RequestParam(value = "key") String key, HttpServletRequest request) {
        return userRepository.findOneByLogin(organizationUserRegisterDTO.getLogin())
            .map(user -> {
                ErrorDTO errorDTO = new ErrorDTO("login already in use");
                return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
            })
                .orElseGet(() -> {

                    InvitedUser invitedUser = invitedUserRepository.findOneByActivationKey(key);
                    if(invitedUser == null){
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                    OrganizationUser organizationUser = organizationUserRepository.findOneByUserId(invitedUser.getId());
                    if(organizationUser == null){
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                    organizationUserService.createUserInformation(organizationUserRegisterDTO, invitedUser, organizationUser);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                });

    }
}
