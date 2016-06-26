package com.ozay.backend.service;

import com.ozay.backend.domain.User;
import com.ozay.backend.model.*;
import com.ozay.backend.repository.*;
import com.ozay.backend.utility.HTMLUtility;
import com.ozay.backend.web.rest.form.CollaborateCreateFormDTO;
import com.ozay.backend.web.rest.form.CollaborateDetailFieldDTO;
import com.ozay.backend.web.rest.form.CollaborateDetailFormDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by naofumiezaki on 11/18/15.
 */
@Service
@Transactional
public class CollaborateService {


    @Inject
    CollaborateRepository collaborateRepository;

    @Inject
    CollaborateFieldRepository collaborateFieldRepository;

    @Inject
    CollaborateMemberRepository collaborateMemberRepository;

    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @Inject
    MailService mailService;

    @Inject
    MemberRepository memberRepository;

    public void complete(Collaborate collaborate){
        collaborate.setStatus(Collaborate.STATUS_COMPLETED);
        collaborateRepository.update(collaborate);
        List<Member> members = memberRepository.findAllByCollaborateId(collaborate.getId());
        mailService.sendCollaborateComplete(collaborate, members);
    }

    public void cancel(Collaborate collaborate){
        collaborate.setStatus(Collaborate.STATUS_CANCELED);
        collaborateRepository.update(collaborate);
        List<Member> members = memberRepository.findAllByCollaborateId(collaborate.getId());
        mailService.sendCollaborateComplete(collaborate, members);
    }

    public void update(CollaborateDetailFormDTO collaborateDetailFormDTO){

        for(CollaborateDetailFieldDTO field : collaborateDetailFormDTO.getCollaborateTrackField()){
            collaborateMemberRepository.update(collaborateDetailFormDTO.getCollaborateId(), field.getCollaborateFieldId(), collaborateDetailFormDTO.getMemberId(), field.getSelected());
        }

        Collaborate collaborate = collaborateRepository.findOneById(collaborateDetailFormDTO.getCollaborateId());

        User user = userRepository.findOne(collaborate.getCreatedBy());
        Member replyFrom = memberRepository.findOne(collaborateDetailFormDTO.getMemberId());
        mailService.sendCollaborateUpdate(collaborate,replyFrom, user.getEmail());
    }


    public void create(CollaborateCreateFormDTO collaborateCreateFormDTO){
        Collaborate collaborate = collaborateCreateFormDTO.getCollaborate();
        collaborate.setStatus(Collaborate.STATUS_CREATED);
        User user = userService.getUserWithAuthorities();

        collaborate.setCreatedBy(user.getId());
        collaborate.setMessage(HTMLUtility.removeScriptTag(collaborate.getMessage()));
        collaborateRepository.create(collaborate);

        List<Member> members = collaborateCreateFormDTO.getMembers();

        for(CollaborateField collaborateField : collaborateCreateFormDTO.getCollaborateFields()){
            collaborateField.setCollaborateId(collaborate.getId());
            collaborateFieldRepository.create(collaborateField);
            for(Member member : members){
                collaborateMemberRepository.create(collaborate.getId(), collaborateField.getId(), member.getId());
            }
        }
        collaborate.setCollaborateFields(collaborateCreateFormDTO.getCollaborateFields());
        mailService.sendCollaborateMail(collaborate, members);
    }
}
