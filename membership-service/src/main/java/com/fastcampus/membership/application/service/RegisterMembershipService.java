package com.fastcampus.membership.application.service;

import com.fastcampus.membership.adapter.out.persistence.MembershipJpaEntity;
import com.fastcampus.membership.adapter.out.persistence.MembershipMapper;
import com.fastcampus.membership.application.port.in.RegisterMembershipCommand;
import com.fastcampus.membership.application.port.in.RegisterMembershipUseCase;
import com.fastcampus.membership.application.port.out.RegisterMembershipPort;
import com.fastcampus.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final RegisterMembershipPort registerMembershipPort;
    private final MembershipMapper membershipMapper;
    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {
        MembershipJpaEntity jpaEntity = registerMembershipPort.createMembership(
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
        );
        // entity -> Membership
        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
