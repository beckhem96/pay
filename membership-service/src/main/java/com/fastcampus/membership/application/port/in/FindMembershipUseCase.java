package com.fastcampus.membership.application.port.in;

import com.fastcampus.membership.domain.Membership;

public interface FindMembershipUseCase {
    Membership findMembership(FindMembershipCommand command);
}
