package com.ozay.backend.service;

import com.ozay.backend.domain.User;
import com.ozay.backend.model.*;
import com.ozay.backend.repository.*;
import com.ozay.backend.security.SecurityUtils;
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
    CollaborateDateRepository collaborateDateRepository;

    @Inject
    CollaborateMemberRepository collaborateMemberRepository;

    @Inject
    UserService userService;

    @Inject
    MailService mailService;

    @Inject
    MemberRepository memberRepository;

    public void complete(Collaborate collaborate){
        collaborateRepository.update(collaborate);
        List<Member> members = memberRepository.findAllByCollaborateId(collaborate.getId());
        mailService.sendCollaborateComplete(collaborate, members);
    }

    public void cancel(Collaborate collaborate){
        collaborateRepository.update(collaborate);
        List<Member> members = memberRepository.findAllByCollaborateId(collaborate.getId());
        mailService.sendCollaborateComplete(collaborate, members);
    }

    public void update(CollaborateDetailFormDTO collaborateDetailFormDTO){
        for(CollaborateDetailFieldDTO field : collaborateDetailFormDTO.getCollaborateTrackField()){
            collaborateMemberRepository.update(collaborateDetailFormDTO.getCollaborateId(), field.getCollaborateDateId(), collaborateDetailFormDTO.getMemberId(), field.getSelected());
        }
    }


    public void create(CollaborateCreateFormDTO collaborateCreateFormDTO){
        Collaborate collaborate = collaborateCreateFormDTO.getCollaborate();
        User user = userService.getUserWithAuthorities();

        collaborate.setCreatedBy(user.getId());
        collaborate.setMessage(HTMLUtility.removeScriptTag(collaborate.getMessage()));
        collaborateRepository.create(collaborate);

        List<Member> members = collaborateCreateFormDTO.getMembers();

        for(CollaborateDate collaborateDate : collaborateCreateFormDTO.getCollaborateDates()){
            collaborateDate.setCollaborateId(collaborate.getId());
            collaborateDateRepository.create(collaborateDate);
            for(Member member : members){
                collaborateMemberRepository.create(collaborate.getId(), collaborateDate.getId(), member.getId());
            }
        }
        mailService.sendCollaborateCreate(collaborate, members);
    }
}
