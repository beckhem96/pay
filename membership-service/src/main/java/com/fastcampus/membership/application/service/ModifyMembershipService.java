package com.fastcampus.membership.application.service;

import com.fastcampus.membership.adapter.out.persistence.MembershipJpaEntity;
import com.fastcampus.membership.adapter.out.persistence.MembershipMapper;
import com.fastcampus.membership.application.port.in.FindMembershipCommand;
import com.fastcampus.membership.application.port.in.FindMembershipUseCase;
import com.fastcampus.membership.application.port.in.ModifyMembershipCommand;
import com.fastcampus.membership.application.port.in.ModifyMembershipUseCase;
import com.fastcampus.membership.application.port.out.FindMembershipPort;
import com.fastcampus.membership.application.port.out.ModifyMembershipPort;
import com.fastcampus.membership.domain.Membership;
import com.fastcampus.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class ModifyMembershipService implements ModifyMembershipUseCase {
    private final ModifyMembershipPort modifyMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {
        MembershipJpaEntity membershipJpaEntity = modifyMembershipPort.modifyMembership(
                new Membership.MembershipId(command.getMembershipId()),
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
        );
        return membershipMapper.mapToDomainEntity(membershipJpaEntity);
    }
}
