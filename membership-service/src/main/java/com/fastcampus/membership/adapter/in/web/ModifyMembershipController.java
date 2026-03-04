package com.fastcampus.membership.adapter.in.web;

import com.fastcampus.common.WebAdapter;
import com.fastcampus.membership.application.port.in.ModifyMembershipCommand;
import com.fastcampus.membership.application.port.in.ModifyMembershipUseCase;
import com.fastcampus.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyMembershipController {
    private final ModifyMembershipUseCase modifyMembershipUseCase;

    @PostMapping("/membership/modify")
    ResponseEntity<Membership> findMembershipByMemberId(@RequestBody ModifyMembershipRequest request) {
        if ("error".equals(request.getName())) {
            throw new RuntimeException("테스트용 런타임 에러가 발생했습니다!");
        }
        ModifyMembershipCommand command = ModifyMembershipCommand.builder()
                .membershipId(request.getMembershipId())
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .isValid(request.isValid())
                .isCorp(request.isCorp())
                .build();
        return ResponseEntity.ok(modifyMembershipUseCase.modifyMembership(command));
    }
}
