package com.ozay.backend.service;

import com.ozay.backend.model.Member;
import com.ozay.backend.repository.MemberRepository;
import com.ozay.backend.repository.RoleMemberRepository;
import com.ozay.backend.web.rest.form.MemberFormDTO;
import com.ozay.backend.web.rest.form.MemberRoleFormDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by naofumiezaki on 11/29/15.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberService {
    @Inject
    MemberRepository memberRepository;

    @Inject
    RoleMemberRepository roleMemberRepository;

    public void createMember(MemberFormDTO memberFormDTO){
        memberRepository.create(memberFormDTO.getMember());
        this.updateRoleMember(memberFormDTO);
    }

    public void updateMember(MemberFormDTO memberFormDTO){
        memberRepository.update(memberFormDTO.getMember());
        this.updateRoleMember(memberFormDTO);
    }

    public void deleteMember(Member member){
        roleMemberRepository.deleteAllByMemberId(member.getId());
        memberRepository.softDelete(member);
    }

    private void updateRoleMember(MemberFormDTO memberFormDTO){
        roleMemberRepository.deleteAllByMemberId(memberFormDTO.getMember().getId());
        for(MemberRoleFormDTO memberRoleFormDTO : memberFormDTO.getRoles()){
            roleMemberRepository.create(memberRoleFormDTO.getId(), memberFormDTO.getMember().getId());
        }
    }
}
