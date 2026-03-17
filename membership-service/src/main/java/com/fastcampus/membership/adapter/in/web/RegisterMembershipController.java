package com.fastcampus.membership.adapter.in.web;

import com.fastcampus.common.WebAdapter;
import com.fastcampus.membership.application.port.in.RegisterMembershipCommand;
import com.fastcampus.membership.application.port.in.RegisterMembershipUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {

    private final RegisterMembershipUseCase registerMembershipUseCase;

    @GetMapping("/test-error")
    public String testError() {
        throw new RuntimeException("K8s 에러 테스트!!!");
    }

    @PostMapping("/membership/register")
    com.fastcampus.membership.domain.Membership register(@RequestBody RegisterMembershipRequest request) {
        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .isValid(true)
                .isCorp(request.isCorp())
                .build();

        return registerMembershipUseCase.registerMembership(command);
    }
}
