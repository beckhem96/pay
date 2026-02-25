package com.fastcampus.membership.application.port.out;

import com.fastcampus.membership.adapter.out.persistence.MembershipJpaEntity;
import com.fastcampus.membership.domain.Membership;

public interface FindMembershipPort {
    MembershipJpaEntity findMembership(
        Membership.MembershipId membershipId
    );
}
