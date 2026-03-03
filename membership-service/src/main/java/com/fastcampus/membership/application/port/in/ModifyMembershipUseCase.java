package com.fastcampus.membership.application.port.in;

import com.fastcampus.membership.domain.Membership;

public interface ModifyMembershipUseCase {
    Membership modifyMembership(ModifyMembershipCommand command);
}
