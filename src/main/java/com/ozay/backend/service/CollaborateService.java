package com.ozay.backend.service;

import com.ozay.backend.model.Collaborate;
import com.ozay.backend.model.CollaborateRecord;
import com.ozay.backend.model.Member;
import com.ozay.backend.repository.BuildingRepository;
import com.ozay.backend.repository.CollaborateRecordRepository;
import com.ozay.backend.repository.CollaborateRepository;
import com.ozay.backend.repository.MemberRepository;
import com.ozay.backend.web.rest.form.CollaborateFormDTO;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by RGV Krushnan on 25-03-2016.
 */
public class CollaborateService {

    @Inject
    CollaborateRepository collaborateRepository;

    @Inject
    MemberRepository memberRepository;

    @Inject
    @Autowired(required = true)
    CollaborateRecordRepository collaborateRecordRepository;

    @Inject
    BuildingRepository buildingRepository;

    @Inject
    MailService mailService;

    private static final String EMAIL_PATTERN =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public void processCollaborate(CollaborateFormDTO collaborateFormDTO) throws Exception {
        Collaborate collaborate = collaborateFormDTO.getCollaborate();
        collaborate.setSurveyissueDate(new DateTime());
        collaborateRepository.create(collaborate);

        //ArrayList<String> emails = new ArrayList<String>();
        List<CollaborateRecord> collaborateRecords = new ArrayList<CollaborateRecord>();

        Set<Long> ids = new HashSet<Long>();
        for(Member member : collaborateFormDTO.getMembers()){
            ids.add(member.getId());
        }

        List<Member> members = memberRepository.findAllByIds(ids);

        for(Member member:members){
            CollaborateRecord collaborateRecord = new CollaborateRecord();
            collaborateRecord.setCollaborateId(collaborate.getId());
            collaborateRecordRepository.create(collaborateRecord);
        }



    }
}
