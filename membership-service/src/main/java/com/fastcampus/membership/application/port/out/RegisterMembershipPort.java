package com.fastcampus.membership.application.port.out;

import com.fastcampus.membership.adapter.out.persistence.MembershipJpaEntity;
import com.fastcampus.membership.domain.Membership;

public interface RegisterMembershipPort {
    MembershipJpaEntity createMembership(
            Membership.MembershipName membershipName
            , Membership.MembershipEmail membershipEmail
            , Membership.MembershipAddress membershipAddress
            , Membership.MembershipIsValid membershipIsValid
            ,Membership.MembershipIsCorp membershipIsCorp
    );
}
